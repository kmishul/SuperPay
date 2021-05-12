package com.project.wallet_service.util;

import com.project.wallet_service.model.Transaction;

public class TransactionValidator {
    public static boolean validateRequest (Transaction request){
        if(request.getAmount()<1 || request.getRid()<0 || request.getSid()<0)
            return false;
        return true;
    }
}
