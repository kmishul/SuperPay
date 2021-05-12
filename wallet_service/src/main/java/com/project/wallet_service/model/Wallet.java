package com.project.wallet_service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "balance")
    private int balance;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "wallet_type")
    private String walletType;

}
