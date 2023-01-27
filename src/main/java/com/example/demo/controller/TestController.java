package com.example.demo.controller;

import com.example.demo.exceptions.CustomException;
import com.example.demo.exceptions.exceptionMessages.ErrorMessage;
import com.example.demo.domain.Test;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.repo.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class TestController extends ParentController {

    @Autowired
    TestRepo testRepo;
    @Autowired
    KafkaProducer kafkaProducer;

    @GetMapping(value = "/test")
    public ResponseEntity<List<Test>> getCall(){
        kafkaProducer.sendMessage("Hello Shakeeb "+ LocalDateTime.now());
        List<Test> tests=testRepo.findAll();
        ResponseEntity<List<Test>> testResponseEntity;
        testResponseEntity= new ResponseEntity<>(tests,HttpStatus.FOUND);
        return testResponseEntity;
    }

    @GetMapping(value = "/test/{id}")
    public ResponseEntity<?> getCall(@PathVariable int id){
        Optional<Test> test=testRepo.findById(id);
        ResponseEntity<?> testResponseEntity;
        if(!test.isPresent())
        {
            throw new CustomException("Not Found");
        }
        testResponseEntity= new ResponseEntity<>(test.get(),HttpStatus.FOUND);
        return testResponseEntity;
    }

}
