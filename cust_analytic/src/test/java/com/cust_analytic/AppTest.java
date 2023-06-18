package com.cust_analytic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.ThreadLocalRandom;   

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    void testApp() {
        assertEquals(1, 1);
    }

      @Test
    void kafkaProducerTest1() throws Exception{
        double amount = ThreadLocalRandom.current().nextDouble(10000);
        Payment p = new Payment("O1", "C1", amount, "2020-11-12T09:02:00.000Z");
        ProducerFactory pf = new ProducerFactory();
        long  offset = pf.send("P1", p);   
        Assertions.assertTrue(offset > 0);
    }

      @Test
    void kafkaProducerNegAmt() throws Exception{
        double amount = -1;
        Payment p = new Payment("O1", "C1", amount, "2020-11-12T09:02:00.000Z");
        ProducerFactory pf = new ProducerFactory();
        long  offset = pf.send("P1", p);   
        Assertions.assertTrue(offset > 0);
    }
}
