package com.cust_analytic;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.serialization.Serializer;

public class ProductSerializer implements Serializer<Product> {
  private Gson gson = new Gson();

  @Override
  public byte[] serialize(String topic, Product  product) {
    if (product == null) return null;
    return gson.toJson(product).getBytes(StandardCharsets.UTF_8);
  }
}
