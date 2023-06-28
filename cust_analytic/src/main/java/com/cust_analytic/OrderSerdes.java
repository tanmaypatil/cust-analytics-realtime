package com.cust_analytic;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class OrderSerdes implements Serde<Order> {
    @Override
  public Serializer<Order> serializer() {
    return new OrderSerializer();
  }

  @Override
  public Deserializer<Order> deserializer() {
    return new OrderDeserializer();
  }

}
