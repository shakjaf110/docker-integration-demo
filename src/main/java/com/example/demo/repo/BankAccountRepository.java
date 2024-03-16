package com.example.demo.repo;

import com.example.demo.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<BankAccount> findWithLockingById(int id);
}
