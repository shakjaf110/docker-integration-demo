package com.example.demo.controller;

import com.example.demo.domain.BankAccount;
import com.example.demo.domain.Transaction;
import com.example.demo.exceptions.CustomException;
import com.example.demo.repo.BankAccountRepository;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class BankAccountController {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<BankAccount>> getCall(){
        return new ResponseEntity<>(bankAccountRepository.findAll(),HttpStatus.FOUND);
    }

    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<?> getCall(@PathVariable int id){
        Optional<BankAccount> bankAccount= bankAccountRepository.findById(id);
        ResponseEntity<?> testResponseEntity;
        if(!bankAccount.isPresent())
        {
            throw new CustomException("Not Found");
        }
        testResponseEntity= new ResponseEntity<>(bankAccount.get(),HttpStatus.FOUND);
        return testResponseEntity;
    }

    @PostMapping(value = "/accounts")
    public ResponseEntity<BankAccount> getCall(@RequestBody BankAccount bankAccount){
        return new ResponseEntity<>(bankAccountRepository.save(bankAccount),HttpStatus.CREATED);
    }


    @PutMapping(value = "/accounts/{id}")
    public ResponseEntity<BankAccount> getCall(@PathVariable int id, @RequestBody BankAccount bankAccount){
        if(!bankAccountRepository.existsById(id))
        {
            throw new CustomException("Not Found");
        }
        return new ResponseEntity<>(bankAccountRepository.save(bankAccount),HttpStatus.CREATED);
    }

}
