package com.cust_analytic;

import java.util.Properties;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.HostInfo;

/**
 * Hello Cust stats
 */
public final class App {
    private App() {
    }

    /**
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Hello Cust Analytics!");
        // we allow the following system properties to be overridden
        String host = System.getProperty("host");
        System.out.println(" host " + host);
        //Integer port = Integer.parseInt(System.getProperty("port"));
        Integer port = 8082;
        Topology topology = CustTopology.build();

        // set the required properties for running Kafka Streams
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // build the topology and start streaming!
        KafkaStreams streams = new KafkaStreams(topology, config);
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        System.out.println("Starting Cust analytics stream");

        streams.start();
        HostInfo hostInfo = new HostInfo(host, port);
        RestService service = new RestService(hostInfo, streams);
        service.start();
    }
}
