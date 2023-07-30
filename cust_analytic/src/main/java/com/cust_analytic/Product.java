package com.cust_analytic;

import com.google.gson.annotations.SerializedName;

public class Product implements IMessage {
    @SerializedName("ProductId")
    String productId;
    @SerializedName("ProductName")
    String productName;
    @SerializedName("ProductDesc")
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
