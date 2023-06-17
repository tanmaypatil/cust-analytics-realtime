package com.cust_analytic;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class PaymentSerdes implements Serde<Payment> {
    @Override
  public Serializer<Payment> serializer() {
    return new PaymentSerializer();
  }

  @Override
  public Deserializer<Payment> deserializer() {
    return new PaymentDeserializer();
  }

}
