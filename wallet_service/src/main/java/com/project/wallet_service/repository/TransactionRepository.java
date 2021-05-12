package com.project.wallet_service.repository;

import com.project.wallet_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("select u from Transaction u where u.sid = ?1 OR u.rid = ?1")
    List<Transaction> findBySidOrRid(int id);
}
