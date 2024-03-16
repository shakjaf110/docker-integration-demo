package com.example.demo.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(columnDefinition = "bigint DEFAULT 0")
    private long amount;

    @Version
    @Column(columnDefinition = "bigint DEFAULT 0")
    long version;

    @JoinColumn(name = "bank_account_id")
    @ManyToOne
    private BankAccount bankAccount;
}
