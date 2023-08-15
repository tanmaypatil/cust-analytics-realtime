package com.cust_analytic;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class MessageConsumer {
    KafkaConsumer<String, IMessage> consumer = null;
    Properties props = new Properties();
    final String valueDeSerializer = "com.cust_analytic.";

    private void setValueDeSerializer(String valueClass) {
        String valClass = valueDeSerializer + valueClass;
        props.put("value.deserializer", valClass);
    }

    public MessageConsumer(String valueClass) {
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        setValueDeSerializer(valueClass);
        consumer = new KafkaConsumer<>(props);
    }

    public IMessage receive(String topic) {
        IMessage rec = null; 
        consumer.subscribe(Arrays.asList(topic));
        ConsumerRecords<String, IMessage> records = consumer.poll(Duration.ofMillis(5000));
        // check if we timed out 
        if (records.count() > 0) {
            System.out.println("message consumer retrieved record "+records.count());
            for (ConsumerRecord<String, IMessage> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                rec = record.value();
                break;
            }
        } 
        return rec;
    }
}