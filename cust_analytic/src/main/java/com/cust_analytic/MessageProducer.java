package com.cust_analytic;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.Metadata;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class MessageProducer {
    Producer<String, IMessage> producer = null;

    public  MessageProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "com.cust_analytic.PaymentSerializer");
        properties.put("acks", "1");
        properties.put("retries", "3");
        properties.put("compression.type", "snappy");

        producer = new KafkaProducer<>(properties);
      
    }

    public long  send(String id, IMessage value,String topic) {
        Payment p = (Payment) value;
        RecordMetadata metadata = null ;
        ProducerRecord<String , IMessage> record = new ProducerRecord<String,IMessage>(topic, id, p);
        Future<RecordMetadata> sendFuture = producer.send(record);
        try {
            metadata = sendFuture.get();
        } catch (ExecutionException ee) {
            // the computation threw an exception
            System.out.println("Execution exception "+ee);
        } catch (InterruptedException ie) {
            // the current thread was interrupted while waiting
            System.out.println("interrupted exception "+ie);
        } 
        long  offset = metadata.offset();
        System.out.println("offset is "+offset);
        return offset;
    }

}
