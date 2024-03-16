package com.example.demo.config;

import com.example.demo.domain.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TransactionService transactionService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Transaction transaction=new Transaction();
        transaction.setId(1);
        transaction.setAmount(20);
        transaction.setName("Shopping");
        executor.execute(safeRunnable(() -> transactionService.saveOrUpdate(21, transaction)));

        Transaction transaction1=new Transaction();
        transaction1.setId(1);
        transaction1.setAmount(100);
        transaction1.setName("ATM");
        executor.execute(safeRunnable(() -> transactionService.saveOrUpdate(21, transaction1)));

        executor.shutdown();
    }

    private Runnable safeRunnable(Runnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
