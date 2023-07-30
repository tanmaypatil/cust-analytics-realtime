package com.cust_analytic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
        /* 
        String topics[] = { "products","payments" };
        KafkaUtils k = new KafkaUtils();
        boolean status = k.deleteTopics(topics);
        Assertions.assertTrue(status);
        String[] list = k.listTopics();
        System.out.println(Arrays.toString(list));
        status = k.createTopics(topics);
        Assertions.assertTrue(status);
        */


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
    @DisplayName("join between products ( K-Table ) and Payments(K-Streams)")
    void kafkaProductPaymentJoinTest1() throws Exception {
        long new_offset = -1;
        MessageProducer pf = new MessageProducer("ProductSerializer");
        // First product
        Product pr = new Product("PRD1", "Book", "Black Swan");
        long offset = pf.send("PRD1", pr, "products");
        Assertions.assertTrue(offset >= 0);
        // second product
        pr = new Product("PRD2", "Book", "An Officer and Spy");
        new_offset = pf.send("PRD2", pr, "products");
        Assertions.assertTrue(new_offset > offset);
        offset = new_offset;
        // third product
        pr = new Product("PRD3", "Eletronics", "Apple iWatch");
        new_offset = pf.send("PRD3", pr, "products");
        Assertions.assertTrue(new_offset > offset);
        double amount = ThreadLocalRandom.current().nextDouble(10000);
        String custId = getCustId();
        String orderId = getOrderId();
        offset = new_offset;
        // first payment P1
        Payment p = new Payment("PRD1", orderId, custId, amount, "2020-11-12T09:02:00.000Z");
        pf = new MessageProducer("PaymentSerializer");
        new_offset = pf.send("P1", p, "payments");
        Assertions.assertTrue(new_offset >= 0);
        offset = new_offset;
        // payment P2
        amount = ThreadLocalRandom.current().nextDouble(10000);
        p = new Payment("PRD1", orderId, custId, amount, "2020-11-13T09:02:00.000Z");
        pf = new MessageProducer("PaymentSerializer");
        new_offset = pf.send("P2", p, "payments");
        Assertions.assertTrue(new_offset > offset);
        offset = new_offset;
        // payment P3
        amount = ThreadLocalRandom.current().nextDouble(10000);
        p = new Payment("PRD2", orderId, custId, amount, "2020-11-12T09:02:00.000Z");
        pf = new MessageProducer("PaymentSerializer");
        new_offset = pf.send("P3", p, "payments");
        Assertions.assertTrue(new_offset > offset);
        offset = new_offset;
        // payment P4
        amount = ThreadLocalRandom.current().nextDouble(10000);
        p = new Payment("PRD2", orderId, custId, amount, "2020-11-13T09:02:00.000Z");
        pf = new MessageProducer("PaymentSerializer");
        new_offset = pf.send("P4", p, "payments");
        Assertions.assertTrue(new_offset > offset);
    }

    // Delete topic test . test is not repeatable.
    // On a deleted topic , we would get interruped exception.
    @Test
    public void deleteTopics() {
        String topics[] = { "products","payments" };
        KafkaUtils k = new KafkaUtils();
        boolean status = k.deleteTopics(topics);
        Assertions.assertTrue(status);

    }

    @Test
    public void createTopics() {
        String topics[] = { "products","payments" };
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

    @Test 
    public void roundNumber() {
        double amount = ThreadLocalRandom.current().nextDouble(10000);
        double amt = Util.round(amount, 2);
        System.out.println("amt "+amt);

    }
}
