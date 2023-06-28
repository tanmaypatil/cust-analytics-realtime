package com.cust_analytic;

public class Product {
    String productName;
    String ProductDesc;

    public Product(String productName, String productDesc) {
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
