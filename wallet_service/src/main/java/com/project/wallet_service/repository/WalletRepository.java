package com.project.wallet_service.repository;

import com.project.wallet_service.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query("SELECT w FROM Wallet w WHERE w.userId = ?1")
    Wallet findWalletByUserId(int userId);
}
