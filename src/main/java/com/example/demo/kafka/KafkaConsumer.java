package com.example.demo.kafka;

import com.example.demo.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topicPartitions = @TopicPartition(topic = AppConstants.TOPIC_NAME, partitionOffsets = {
            @PartitionOffset(partition = "0", initialOffset = "5"),
            @PartitionOffset(partition = "1", initialOffset = "0"),
            @PartitionOffset(partition = "2", initialOffset = "0"),
            @PartitionOffset(partition = "3", initialOffset = "0"),
            @PartitionOffset(partition = "4", initialOffset = "0"),
    }))
    public void listen(@Payload String foo, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println("Received: " + foo + " (partition: " + partition + ")");
    }

    @KafkaListener(topics = AppConstants.TOPIC_NAME,
            groupId = AppConstants.GROUP_ID)
    public void consume(String message){
        LOGGER.info(String.format("Message received -> %s", message));
    }

}