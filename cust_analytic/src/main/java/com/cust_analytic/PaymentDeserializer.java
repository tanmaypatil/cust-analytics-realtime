package com.cust_analytic;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PaymentDeserializer implements Deserializer<Payment> {
    private Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

    @Override
    public Payment deserialize(String topic, byte[] bytes) {
        if (bytes == null)
            return null;
        return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), Payment.class);
    }

}
