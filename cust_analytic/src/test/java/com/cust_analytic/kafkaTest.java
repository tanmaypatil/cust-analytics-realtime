package com.cust_analytic;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.cust_analytic.kafkaUtils.KafkaUtils;

public class kafkaTest {
    // Delete topic test . test is not repeatable.
    // On a deleted topic , we would get interruped exception.
    @Test
    public void deleteTopics() {
        String topics[] = { "products", "payments" };
        KafkaUtils k = new KafkaUtils();
        boolean status = k.deleteTopics(topics);
        Assertions.assertTrue(status);

    }

    @Test
    public void createTopics() {
        String topics[] = { "products", "payments" };
        KafkaUtils k = new KafkaUtils();
        boolean status = k.createTopics(topics);
        Assertions.assertTrue(status);
    }

    @Test
    public void listTopics() {
        String[] topics = null;
        KafkaUtils k = new KafkaUtils();
        topics = k.listTopics();
        System.out.println(Arrays.toString(topics));
        Assertions.assertNotNull(topics);
    }

}
