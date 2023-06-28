package com.cust_analytic;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer<T> implements Serializer<T>{
     private Gson gson =
      new GsonBuilder()
          .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
          .create();

  /** Default constructor needed by Kafka */
  public JsonSerializer() {}

  @Override
  public void configure(Map props, boolean isKey) {}

  @Override
  public byte[] serialize(String topic, T type) {
    return gson.toJson(type).getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public void close() {}
    
}
