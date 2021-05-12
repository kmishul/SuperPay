package com.project.wallet_service.exception;

public class TransactionBadRequest extends RuntimeException {

    public TransactionBadRequest(){
        super("TransactionBadRequest");
    }
}
