package com.project.wallet_service.service;

import com.project.wallet_service.model.Wallet;
import com.project.wallet_service.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public List<Wallet> findAll() {
        return  walletRepository.findAll();
    }

    public Optional<Wallet> findById(int id) {
        return walletRepository.findById(id);
    }

    public Wallet save(Wallet newWallet) {
        return walletRepository.save(newWallet);
    }

    public Wallet findWalletByUserId(int id) {
    return walletRepository.findWalletByUserId(id);
    }
}
