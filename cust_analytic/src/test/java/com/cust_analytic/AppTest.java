package com.cust_analytic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.cust_analytic.kafkaUtils.KafkaUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * Unit test for Kafka streaming app
 */
@TestInstance(Lifecycle.PER_CLASS)
class AppTest {
    SyncSequenceGenerator seq = new SyncSequenceGenerator();
    SyncSequenceGenerator pseq = new SyncSequenceGenerator();

    @BeforeAll
    private void initTopics() {

    }

    String getOrderId() {
        String orderId = "O" + seq.getNext();
        return orderId;
    }

    String getCustId() {
        int CustIdNum = ThreadLocalRandom.current().nextInt(10000);
        String custId = "C" + CustIdNum;
        return custId;
    }

    @Test
    void testApp() {
        assertEquals(1, 1);
    }

    @Test
    void kafkaProducerTest1() throws Exception {
        double amount = ThreadLocalRandom.current().nextDouble(10000);
        String custId = getCustId();
        String orderId = getOrderId();
        Payment p = new Payment("PRD1", orderId, custId, amount, "2020-11-12T09:02:00.000Z");
        MessageProducer pf = new MessageProducer("PaymentSerializer");
        long offset = pf.send("P1", p, "payments");
        Assertions.assertTrue(offset > 0);
    }

    @Test
    void kafkaProducerNegAmt() throws Exception {
        double amount = -1;
        String custId = getCustId();
        String orderId = getOrderId();
        Payment p = new Payment("PRD1", orderId, custId, amount, "2020-11-13T09:02:00.000Z");
        MessageProducer pf = new MessageProducer("PaymentSerializer");
        long offset = pf.send("P1", p, "payments");
        Assertions.assertTrue(offset > 0);
    }

    @Test
    void kafkaProductPaymentJoinTest1() throws Exception {
        MessageProducer pf = new MessageProducer("ProductSerializer");
        // First product
        Product pr = new Product("PRD1", "Book", "Black Swan");
        long offset = pf.send("PRD1", pr, "products");
        Assertions.assertTrue(offset > 0);
        // second product
        pr = new Product("PRD2", "Book", "An Officer and Spy");
        offset = pf.send("PRD1", pr, "products");
        Assertions.assertTrue(offset > 0);
        // third product
        pr = new Product("PRD3", "Eletronics", "Apple iWatch");
        offset = pf.send("PRD3", pr, "products");
        double amount = ThreadLocalRandom.current().nextDouble(10000);
        String custId = getCustId();
        String orderId = getOrderId();
        // first payment P1
        Payment p = new Payment("PRD1", orderId, custId, amount, "2020-11-12T09:02:00.000Z");
        pf = new MessageProducer("PaymentSerializer");
        offset = pf.send("P1", p, "payments");
        Assertions.assertTrue(offset > 0);
        // payment P2
        p = new Payment("PRD1", orderId, custId, amount, "2020-11-13T09:02:00.000Z");
        pf = new MessageProducer("PaymentSerializer");
        offset = pf.send("P2", p, "payments");
        Assertions.assertTrue(offset > 0);
        // payment P3
        p = new Payment("PRD2", orderId, custId, amount, "2020-11-12T09:02:00.000Z");
        pf = new MessageProducer("PaymentSerializer");
        offset = pf.send("P1", p, "payments");
        Assertions.assertTrue(offset > 0);
        // payment P4
        p = new Payment("PRD2", orderId, custId, amount, "2020-11-13T09:02:00.000Z");
        pf = new MessageProducer("PaymentSerializer");
        offset = pf.send("P2", p, "payments");
        Assertions.assertTrue(offset >= 0);
    }

    // Delete topic test . test is not repeatable.
    // On a deleted topic , we would get interruped exception.
    @Test
    public void deleteTopics() {
        String topics[] = { "products" };
        KafkaUtils k = new KafkaUtils();
        boolean status = k.deleteTopics(topics);
        Assertions.assertTrue(status);

    }

    @Test
    public void createTopics() {
        String topics[] = { "products" };
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
