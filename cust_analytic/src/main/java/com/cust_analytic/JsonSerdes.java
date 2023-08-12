package com.cust_analytic;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class JsonSerdes {
    public static Serde<Payment> Payment() {
    JsonSerializer<Payment> serializer = new JsonSerializer<>();
    JsonDeserializer<Payment> deserializer = new JsonDeserializer<>(Payment.class);
    return Serdes.serdeFrom(serializer, deserializer);
  }

  public static Serde<Order> Order() {
    JsonSerializer<Order> serializer = new JsonSerializer<>();
    JsonDeserializer<Order> deserializer = new JsonDeserializer<>(Order.class);
    return Serdes.serdeFrom(serializer, deserializer);
  }

   public static Serde<Product> Product() {
    JsonSerializer<Product> serializer = new JsonSerializer<>();
    JsonDeserializer<Product> deserializer = new JsonDeserializer<>(Product.class);
    return Serdes.serdeFrom(serializer, deserializer);
  }

  public static Serde<ProductSummary> ProductSummary() {
    JsonSerializer<ProductSummary> serializer = new JsonSerializer<>();
    JsonDeserializer<ProductSummary> deserializer = new JsonDeserializer<>(ProductSummary.class);
    return Serdes.serdeFrom(serializer, deserializer);
  }

  public static Serde<PaymentWithProduct> PaymentWithProduct() {
    JsonSerializer<PaymentWithProduct> serializer = new JsonSerializer<>();
    JsonDeserializer<PaymentWithProduct> deserializer = new JsonDeserializer<>(PaymentWithProduct.class);
    return Serdes.serdeFrom(serializer, deserializer);
  }
}
