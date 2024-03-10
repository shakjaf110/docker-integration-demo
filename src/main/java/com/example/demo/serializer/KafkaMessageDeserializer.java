package com.example.demo.serializer;

import com.example.demo.kafka.KafkaMessage;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

/**
 * Custom Kafka message deserializer for deserializing byte arrays into {@link KafkaMessage} objects.
 *
 * @param <T> The type of data within the Kafka message.
 */
@Getter
@Setter
@AllArgsConstructor
public class KafkaMessageDeserializer<T> implements Deserializer<KafkaMessage<T>> {

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
     * Deserializes a byte array into a Kafka message.
     *
     * @param topic The topic associated with the data (not used in this implementation).
     * @param data  The byte array to be deserialized.
     * @return A Kafka message deserialized from the byte array.
     */
    @SneakyThrows
    @Override
    public KafkaMessage<T> deserialize(String topic, byte[] data) {
        String jsonString = new String(data, StandardCharsets.UTF_8);
        return getJacksonObjectMapper().readValue(jsonString, KafkaMessage.class);
    }

    /**
     * Closes the deserializer.
     */
    @Override
    public void close() {
        Deserializer.super.close();
    }


}
