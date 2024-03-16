package com.example.demo.service;

import com.example.demo.domain.BankAccount;
import com.example.demo.domain.Transaction;
import com.example.demo.exceptions.CustomException;
import com.example.demo.kafka.KafkaMessage;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.repo.BankAccountRepository;
import com.example.demo.repo.TransactionRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    KafkaProducer kafkaProducer;

    public List<Transaction> findAll(){
        var tests= transactionRepository.findAll();
        String key=String.valueOf(Instant.now().getEpochSecond());
        kafkaProducer.produce(key, new KafkaMessage(tests));
        return tests;
    }

    public Optional<Transaction> findById(int id){
        return transactionRepository.findById(id);
    }

    public Transaction save(Transaction test){
        return transactionRepository.save(test);
    }

    @Transactional(isolation= Isolation.READ_UNCOMMITTED)
    @SneakyThrows
    public Transaction saveOrUpdate(int bankAccountId,Transaction transaction) {
        Optional<BankAccount> backAccountOptional = bankAccountRepository.findById(bankAccountId);
        if(backAccountOptional.isPresent()){
            BankAccount bankAccount=backAccountOptional.get();
            if(bankAccount.getBalance() < transaction.getAmount())
            {
                throw new CustomException("Not enough balance");
            }
            bankAccount.setBalance(bankAccount.getBalance()-transaction.getAmount());
            transaction.setBankAccount(bankAccount);
        }else {
            throw new CustomException("No account Available");
        }
        Thread.sleep(1000);
        System.out.println(transaction.getAmount()+ " commit: " + Thread.currentThread());
        return transactionRepository.save(transaction);
    }

}
