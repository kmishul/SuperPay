package com.project.wallet_service.service;

import com.project.wallet_service.model.Transaction;
import com.project.wallet_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;


    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Object findBySidOrRid(int id1) {
        return transactionRepository.findBySidOrRid(id1);
    }
}
