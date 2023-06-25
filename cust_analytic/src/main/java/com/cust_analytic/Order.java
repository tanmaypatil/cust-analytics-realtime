package com.cust_analytic;

public class Order {
    private String orderId;
    private String productId;
    private String quantity;
    private String location;

    public Order( String productId, String quantity, String location , SequenceGenerator orderSeq) {
        generateOrderId(orderSeq);
        this.productId = productId;
        this.quantity = quantity;
        this.location = location;
    }

    private void generateOrderId(SequenceGenerator orderSeq) {
        this.orderId = "O" + orderSeq.getNext();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
