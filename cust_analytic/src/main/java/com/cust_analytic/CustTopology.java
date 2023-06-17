package com.cust_analytic;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;

public class CustTopology {
    public static Topology build() {
        // the builder is used to construct the topology
        StreamsBuilder builder = new StreamsBuilder();

         KStream<byte[], Payment> stream =
        builder.stream("payments", Consumed.with(Serdes.ByteArray(), new PaymentSerdes()));
        stream.print(Printed.<byte[], Payment>toSysOut().withLabel("payment-stream"));

        // Filter out failed payments 
           
    KStream<byte[], Payment> filtered =
        stream.filterNot(
            (key, payment) -> {
              return payment.isValid();
            });



        return builder.build();
    }
    
}
