package com.cust_analytic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.ThreadLocalRandom;   

/**
 * Unit test for simple App.
 */
class AppTest {
    SyncSequenceGenerator seq = new SyncSequenceGenerator();
    SyncSequenceGenerator pseq = new SyncSequenceGenerator();

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
    void kafkaProducerTest1() throws Exception{
        double amount = ThreadLocalRandom.current().nextDouble(10000);
        String custId = getCustId();
        String orderId = getOrderId();
        Payment p = new Payment(orderId, custId, amount, "2020-11-12T09:02:00.000Z");
        ProducerFactory pf = new ProducerFactory();
        long  offset = pf.send("P1", p);   
        Assertions.assertTrue(offset > 0);
    }

      @Test
    void kafkaProducerNegAmt() throws Exception{
        double amount = -1;
        String custId = getCustId();
        String orderId = getOrderId();
        Payment p = new Payment(orderId, custId, amount, "2020-11-13T09:02:00.000Z");
        ProducerFactory pf = new ProducerFactory();
        long  offset = pf.send("P1", p);   
        Assertions.assertTrue(offset > 0);
    }
}
