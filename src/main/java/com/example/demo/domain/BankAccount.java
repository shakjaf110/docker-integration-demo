package com.example.demo.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(columnDefinition = "bigint DEFAULT 0")
    private long balance;

    @Version
    @Column(columnDefinition = "bigint DEFAULT 0")
    long version;

    @OneToMany(mappedBy = "bankAccount",cascade = CascadeType.ALL)
    List<Transaction> transactions;

    public List<Transaction> addTransaction(Transaction transaction){
        if(transactions==null){
            transactions=new ArrayList<>();
        }
        transactions.add(transaction);
        return transactions;
    }
}
