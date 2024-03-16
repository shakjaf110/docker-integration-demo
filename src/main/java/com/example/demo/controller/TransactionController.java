package com.example.demo.controller;

import com.example.demo.domain.Transaction;
import com.example.demo.exceptions.CustomException;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "/transactions")
    public ResponseEntity<List<Transaction>> getCall(){
        return new ResponseEntity<>(transactionService.findAll(),HttpStatus.FOUND);
    }

    @GetMapping(value = "/transactions/{id}")
    public ResponseEntity<?> getCall(@PathVariable int id){
        Optional<Transaction> transaction= transactionService.findById(id);
        ResponseEntity<?> testResponseEntity;
        if(!transaction.isPresent())
        {
            throw new CustomException("Not Found");
        }
        testResponseEntity= new ResponseEntity<>(transaction.get(),HttpStatus.FOUND);
        return testResponseEntity;
    }

    @PostMapping(value = "/transactions")
    public ResponseEntity<Transaction> getCall(@RequestBody Transaction transaction){
        return new ResponseEntity<>(transactionService.save(transaction),HttpStatus.CREATED);
    }


    @PutMapping(value = "/transactions/{id}")
    public ResponseEntity<Transaction> getCall(@PathVariable int id, @RequestBody Transaction transaction){
        return new ResponseEntity<>(transactionService.saveOrUpdate(id,transaction),HttpStatus.CREATED);
    }

}
