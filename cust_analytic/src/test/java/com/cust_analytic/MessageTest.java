package com.cust_analytic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;


public class MessageTest {

    @DisplayName("create products ")
    @Test
    public void createProduct() {
        MessageProducer pf = new MessageProducer("ProductSerializer");
        long new_offset = 0 ;
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
    }

     @Test
     public void receiveTest() {
        double amount = 1000;
        String custId = Util.getCustId();
        String orderId = Util.getOrderId();
        Payment p = new Payment("PRD1", orderId, custId, amount, "2020-11-12T09:02:00.000Z");
        MessageProducer pf = new MessageProducer("PaymentSerializer");
        // Assumption payments topic is already created.
        long new_offset = pf.send("P18", p, "payments");
        Assertions.assertTrue(new_offset >= 0);
        // Read from kafka topic
        MessageConsumer mc = new MessageConsumer("PaymentDeserializer");
        IMessage rm = mc.receive("payments");
        // Expect to receive message
        Assertions.assertNotNull(rm);
        Payment rp = null ;
        if( rm instanceof Payment) {
           rp = (Payment) rm;
        }
        Assertions.assertEquals("PRD1", rp.getProductId());
     }

     
     @Test
     public void receiveTestPaymentGrouping() {
        double amount = 1000;
        String custId = Util.getCustId();
        String orderId = Util.getOrderId();
        Payment p = new Payment("PRD3", orderId, custId, amount, "2020-11-12T09:02:00.000Z");
        MessageProducer pf = new MessageProducer("PaymentSerializer");
        // Assumption payments topic is already created.
        long new_offset = pf.send("P24", p, "payments");
        Assertions.assertTrue(new_offset >= 0);
        // Read from kafka topic
        MessageConsumer mc = new MessageConsumer("ProductSummaryDeserializer");
        IMessage rm = mc.receive("product-summary");
        // Expect to receive message
        Assertions.assertNotNull(rm);
        ProductSummary rp = null ;
        if( rm instanceof ProductSummary) {
           rp = (ProductSummary) rm;
        }
    
     }

    @Test
     public void receiveTestPaymentGrouping2() {
        double amount = 1000;
        String custId = Util.getCustId();
        String orderId = Util.getOrderId();
        Payment p = new Payment("PRD3", orderId, custId, amount, "2020-11-12T09:02:00.000Z");
        MessageProducer pf = new MessageProducer("PaymentSerializer");
        // Assumption payments topic is already created.
        long new_offset = pf.send("P27", p, "payments");
        Assertions.assertTrue(new_offset >= 0);

        p = new Payment("PRD2", orderId, custId, amount, "2020-11-12T09:02:00.000Z");
        pf = new MessageProducer("PaymentSerializer");
        // Assumption payments topic is already created.
        new_offset = pf.send("P28", p, "payments");
        Assertions.assertTrue(new_offset >= 0);
        // Read from kafka topic
        MessageConsumer mc = new MessageConsumer("ProductSummaryDeserializer");
        IMessage rm = mc.receive("product-summary");
        // Expect to receive message
        Assertions.assertNotNull(rm);
        ProductSummary rp = null ;
        if( rm instanceof ProductSummary) {
           rp = (ProductSummary) rm;
        }
        Assertions.assertTrue(rp.count() > 0);
    
     }
    
}
