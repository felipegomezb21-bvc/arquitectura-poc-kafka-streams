package com.example.all_streams_many_builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import java.util.Map;

public class JsonSerde<T> implements Serde<T> {
    private final Class<T> targetType;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonSerde(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public Serializer<T> serializer() {
        return new Serializer<>() {
            @Override
            public void configure(Map<String, ?> configs, boolean isKey) {
                // No configuration needed
            }

            @Override
            public byte[] serialize(String topic, T data) {
                try {
                    return objectMapper.writeValueAsBytes(data);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void close() {
                // No resources to close
            }
        };
    }

    @Override
    public Deserializer<T> deserializer() {
        return new Deserializer<>() {
            @Override
            public void configure(Map<String, ?> configs, boolean isKey) {
                // No configuration needed
            }

            @Override
            public T deserialize(String topic, byte[] data) {
                try {
                    return objectMapper.readValue(data, targetType);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void close() {
                // No resources to close
            }
        };
    }
}
