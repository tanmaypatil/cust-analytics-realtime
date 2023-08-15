package com.cust_analytic;

import com.google.gson.annotations.SerializedName;

public class PaymentWithProduct {
   @SerializedName("PaymentId")
    String paymentId;
    @SerializedName("PaymentAmount")
    double paymentAmount;
    @SerializedName("ProductId")
    String productId;
    @SerializedName("ProductName")
    String productName;
    @SerializedName("Status")
    String status;

  public PaymentWithProduct(Payment payment, Product product) {
        this.paymentId = payment.getPaymentId();
        this.paymentAmount = payment.getAmount();
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.status = payment.getStatus();
    }

    public String getPaymentId() {
        return paymentId;
    }
    public double getPaymentAmount() {
        return paymentAmount;
    }
    public String getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }

    public String getStatus() {
        return status;
    }
   
  
    
}
