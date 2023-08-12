package com.cust_analytic;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.Duration;

public class CustTopology {
        public static Topology build() {
                // the builder is used to construct the topology
                StreamsBuilder builder = new StreamsBuilder();
                // Payment stream
                KStream<byte[], Payment> stream = builder.stream("payments",
                                Consumed.with(Serdes.ByteArray(), new PaymentSerdes()));
                stream.print(Printed.<byte[], Payment>toSysOut().withLabel("payment-stream"));
                // Product stream
                KTable<String, Product> products = builder.table(
                                "products",
                                Consumed.with(Serdes.String(), JsonSerdes.Product()));

                // Filter out failed payments
                KStream<byte[], Payment> filtered = stream.filterNot(
                                (key, payment) -> {
                                        return payment.isValid();
                                });

                filtered.to("payment-stats", Produced.with(Serdes.ByteArray(), new PaymentSerdes()));

                // Change the key to Product id
                KStream<String, Payment> PaymentEvent = builder
                                .stream("payment-stats", Consumed.with(Serdes.ByteArray(), JsonSerdes.Payment()))
                                .selectKey((k, v) -> v.getProductId().toString());

                ValueJoiner<Payment, Product, PaymentWithProduct> paymentProductJoiner = (payment,
                                product) -> new PaymentWithProduct(payment, product);

                Joined<String, Payment, Product> payProductParams = Joined.with(Serdes.String(), JsonSerdes.Payment(),
                                JsonSerdes.Product(), "payment-product-join");
                // Perform join - Payments and Products
                KStream<String, PaymentWithProduct> paymentsAndProducts = PaymentEvent.join(products,
                                paymentProductJoiner, payProductParams);
                // result of join will be available in topic "payment-products"
                // Group the enriched product stream
                KGroupedStream<String, PaymentWithProduct> grouped = paymentsAndProducts.groupBy(
                                (key, value) -> value.getProductName().toString(),
                                Grouped.with(Serdes.String(), JsonSerdes.PaymentWithProduct()));

                // The initial value of our aggregation will be a new ProductSummary instance
                Initializer<ProductSummary> groupedInitializer = ProductSummary::new;

                Aggregator<String, PaymentWithProduct, ProductSummary> paymentProductAdder = (key, value,
                                aggregate) -> aggregate.add(value);

                // Perform the aggregation, and materialize the underlying state store for
                // querying
                KTable<String, ProductSummary> summary = grouped.aggregate(
                                groupedInitializer,
                                paymentProductAdder, Materialized.<String, ProductSummary, KeyValueStore<Bytes, byte[]>>
                                // give the state store an explicit name to make it available for interactive
                                // queries
                                as("sales-stats")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(JsonSerdes.ProductSummary()));
                
                summary.toStream().to("product-summary");

                /*
                 * paymentsAndProducts.to("payments-products",
                 * Produced.with(Serdes.String(), JsonSerdes.PaymentWithProduct()));
                 */

                return builder.build();
        }

}
