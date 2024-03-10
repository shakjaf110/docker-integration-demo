package com.example.demo.kafka;

import com.example.demo.AppConstants;
import com.example.demo.domain.Test;
import com.example.demo.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class KafkaConsumer {

    //consumer on return all partition wise in topic, no load balancer will implement here as partitionOffsets is set
   /* @KafkaListener(topicPartitions = @TopicPartition(topic = AppConstants.TOPIC_NAME, partitionOffsets = {
            @PartitionOffset(partition = "0", initialOffset = "5"),
            @PartitionOffset(partition = "1", initialOffset = "0"),
            @PartitionOffset(partition = "2", initialOffset = "0"),
            @PartitionOffset(partition = "3", initialOffset = "0"),
            @PartitionOffset(partition = "4", initialOffset = "0"),

    }))
    public void listen(@Payload String foo, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println("1. Received: " + foo + " (partition: " + partition + ")");
    }*/

    @KafkaListener(topics = AppConstants.TOPIC_NAME, groupId = AppConstants.GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    public <T> void consume(ConsumerRecord<String, KafkaMessage<T>> consumerRecord) {
        log.info("ConsumerRecord, Topic: {}, offset: {}, key: {} ", consumerRecord.topic(), consumerRecord.offset(), consumerRecord.key());
        try {
            List<Test> tests =  consumerRecord.value().convertListTo(Test.class);
        } catch (Exception e) {
            log.error("An error occurred for topic: {}, key: {}: {}", consumerRecord.topic(), consumerRecord.key(), e.getMessage());
        }
    }

}