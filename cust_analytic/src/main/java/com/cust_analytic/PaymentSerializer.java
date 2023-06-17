package com.cust_analytic;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.serialization.Serializer;

public class PaymentSerializer implements Serializer<Payment> {
  private Gson gson = new Gson();

  @Override
  public byte[] serialize(String topic, Payment payment) {
    if (payment == null) return null;
    return gson.toJson(payment).getBytes(StandardCharsets.UTF_8);
  }
}
