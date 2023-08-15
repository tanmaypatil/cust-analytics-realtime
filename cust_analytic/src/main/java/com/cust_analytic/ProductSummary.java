package com.cust_analytic;

import java.util.HashMap;
import com.google.gson.annotations.SerializedName;

public class ProductSummary implements IMessage {
    @SerializedName("Aggregate")
    HashMap<String, Double> aggregate = new HashMap<String, Double>();

    public ProductSummary add(final PaymentWithProduct obj) {
        System.out.println(" product name : "+obj.getProductName() + " amount : "+obj.getPaymentAmount());
        if (aggregate.get(obj.getProductName()) != null) {
            Double d = aggregate.get(obj.getProductName());
            d = d + obj.getPaymentAmount();
            System.out.println(" d  = "+d + " payment amount : "+obj.getPaymentAmount());
            aggregate.put(obj.getProductName(), d);
        } else {
            aggregate.put(obj.getProductName(), obj.getPaymentAmount());
        }
        System.out.println("size of hashmap "+aggregate.size());
        return this;
    }

    public int count() {
        if ( this.aggregate != null) {
            return aggregate.size();
        }
        return 0;
    }

    

    

}
