package com.cust_analytic;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;

public class Payment implements IMessage {
    @SerializedName("PaymentId")
    String paymentId;
    @SerializedName("OrderId")
    String orderId;
    @SerializedName("CustId")
    String custId;
    @SerializedName("Amount")
    double amount;
    @SerializedName("TimeStamp")
    String timeStamp;
    @SerializedName("Status")
    String status;
     @SerializedName("ProductId")
    String productId;
    // setter
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public Payment() {

    }


    public Payment(String productId , String orderId, String custId, double amount, String timeStamp) {
        this.productId = productId;
        this.orderId = orderId;
        this.custId = custId;
        this.amount = amount;
        this.timeStamp = timeStamp;
        if (this.amount <= 0)
            this.status = "FAILED";
        else
            this.status = "SUCCESS";
        UUID uuid = UUID.randomUUID();
        this.paymentId = uuid.toString();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return this.custId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isValid() {
        if (this.amount <= 0)
            status = "FAILED";
        else
            status = "SUCCESS";

        return status.equals("FAILED") ? true : false;
    }

}
