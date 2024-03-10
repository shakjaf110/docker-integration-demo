package com.example.demo.kafka;

import com.example.demo.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class KafkaProducer<T> {

    @Autowired
    private KafkaTemplate<String, KafkaMessage<T>> kafkaTemplate;

    public ListenableFuture<SendResult<String, KafkaMessage<T>>> produce(final String key, final KafkaMessage<T> data) {
        log.info(String.format("Message sent, key: %s", key));
        return kafkaTemplate.send(AppConstants.TOPIC_NAME,null, key, data);
    }
}