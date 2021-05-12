package com.project.wallet_service.util;

import com.project.wallet_service.model.Wallet;

public class WalletValidator {
    public static boolean validateWalletRequest(Wallet wallet){
        if(wallet.getUserId()<1 || wallet.getIsActive()==false)
        return false;
        return true;
    }
}