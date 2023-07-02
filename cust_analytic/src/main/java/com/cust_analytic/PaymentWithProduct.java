package com.cust_analytic;

public class PaymentWithProduct {
    String paymentId;
    double paymentAmount;
    String productId;
    String productName;
    public PaymentWithProduct(Payment payment, Product product) {
        this.paymentId = payment.getPaymentId();
        this.paymentAmount = payment.getAmount();
        this.productId = product.getProductName();

    }
    
}
