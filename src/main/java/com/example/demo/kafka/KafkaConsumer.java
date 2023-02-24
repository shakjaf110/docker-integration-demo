package com.example.demo.kafka;

import com.example.demo.AppConstants;
import com.example.demo.exceptions.CustomException;
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
import java.util.Random;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

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

    //consumer on return all partition wise in topic. load balancer implemented by group id since next 2 listener have group id, will be balance the message
    @KafkaListener(topics = AppConstants.TOPIC_NAME,groupId = AppConstants.GROUP_ID)
    public void consume1(@Payload String foo, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, Acknowledgment acknowledgment){
        System.out.println("2. Received: " + foo + " (partition: " + partition + ")");
        acknowledgment.nack(500);
    }
    @KafkaListener(topics = AppConstants.TOPIC_NAME,groupId = AppConstants.GROUP_ID)
    public void consume2(ConsumerRecord<?, ?> consumerRecord, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, Acknowledgment acknowledgment){
        System.out.println("3. Received: " + consumerRecord.value() + " (partition: " + partition + ", offset: "+consumerRecord.offset()+")");
        acknowledgment.acknowledge();
    }

}