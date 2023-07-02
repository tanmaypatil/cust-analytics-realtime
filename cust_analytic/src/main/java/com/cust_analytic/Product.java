package com.cust_analytic;

public class Product implements IMessage {
    String productId;
    String productName;
    String ProductDesc;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    
    public Product(String productId , String productName, String productDesc) {
        this.productId = productId;
        this.productName = productName;
        this.ProductDesc = productDesc;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

}
