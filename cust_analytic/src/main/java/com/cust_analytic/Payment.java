package com.cust_analytic;

import com.google.gson.annotations.SerializedName;

public class Payment {
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

    public Payment() {

    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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
