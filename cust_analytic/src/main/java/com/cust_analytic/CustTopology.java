package com.cust_analytic;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueJoiner;
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

        // Order stream
        KStream<String, Order> orders = builder.stream("orders",
                Consumed.with(Serdes.String(), new OrderSerdes()));

        // Filter out failed payments
        KStream<byte[], Payment> filtered = stream.filterNot(
                (key, payment) -> {
                    return payment.isValid();
                });

        filtered.to("payment-stats", Produced.with(Serdes.ByteArray(), new PaymentSerdes()));

       // Change the key to Product id
        KStream<String, Payment> PaymentEvent = builder
                .stream("payments", Consumed.with(Serdes.ByteArray(), JsonSerdes.Payment()))
                .selectKey((k, v) -> v.getProductId().toString());

        ValueJoiner<Payment, Product, PaymentWithProduct> paymentProductJoiner = (payment,
                product) -> new PaymentWithProduct(payment, product);

        Joined<String, Payment, Product> payOrderParams = Joined.with(Serdes.String(), JsonSerdes.Payment(),
                JsonSerdes.Product(), "payment-order");
        // Perform join - Payments and Products
        KStream<String,PaymentWithProduct> paymentsAndProducts = PaymentEvent.join(products, paymentProductJoiner, payOrderParams);
        paymentsAndProducts.to("payments-products", null);

        return builder.build();
    }

}
