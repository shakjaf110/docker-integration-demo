package com.example.demo.service;

import com.example.demo.domain.Test;
import com.example.demo.repo.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    public List<Test> findAll(){
        return testRepository.findAll();
    }

    public Optional<Test> findById(int id){
        return testRepository.findById(id);
    }
}
