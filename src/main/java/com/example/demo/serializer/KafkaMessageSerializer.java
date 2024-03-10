package com.example.demo.serializer;

import com.example.demo.kafka.KafkaMessage;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serializer;

public class KafkaMessageSerializer <T> implements Serializer<KafkaMessage<T>> {

    private static ObjectMapper jacksonObjectMapper;

    /**
     * Configure JSON converter.
     *
     * @return Configured {@link ObjectMapper}
     */
    public static ObjectMapper getJacksonObjectMapper() {
        if (jacksonObjectMapper == null) {
            jacksonObjectMapper = new ObjectMapper();
            jacksonObjectMapper.registerModule(new JavaTimeModule());
            jacksonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            jacksonObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            jacksonObjectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
            jacksonObjectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
            jacksonObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            jacksonObjectMapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
            jacksonObjectMapper.setVisibility(jacksonObjectMapper.getSerializationConfig()
                    .getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.NON_PRIVATE));
        }

        return jacksonObjectMapper;
    }
    /**
     * Serializes a Kafka message to a byte array.
     *
     * @param topic       The topic associated with the data (not used in this implementation).
     * @param kafkaMessage The Kafka message to be serialized.
     * @return A byte array representing the serialized Kafka message.
     */
    @SneakyThrows
    @Override
    public byte[] serialize(String topic, KafkaMessage<T> kafkaMessage) {
        return getJacksonObjectMapper().writeValueAsString(kafkaMessage).getBytes();
    }

    /**
     * Closes the serializer.
     */
    @Override
    public void close() {
        Serializer.super.close();
    }
}
