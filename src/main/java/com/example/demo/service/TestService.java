package com.example.demo.service;

import com.example.demo.domain.Test;
import com.example.demo.kafka.KafkaMessage;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.repo.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    KafkaProducer kafkaProducer;

    public List<Test> findAll(){
        var tests=testRepository.findAll();
        String key=String.valueOf(Instant.now().getEpochSecond());
        kafkaProducer.produce(key, new KafkaMessage(tests));
        return tests;
    }

    public Optional<Test> findById(int id){
        return testRepository.findById(id);
    }
}
