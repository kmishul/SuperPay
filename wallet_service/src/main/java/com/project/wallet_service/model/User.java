package com.project.wallet_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;

    private String name;

    private String email;

    private String mobile;

    private Boolean isKycDone;
}