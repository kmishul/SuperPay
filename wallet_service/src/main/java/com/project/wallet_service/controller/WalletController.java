package com.project.wallet_service.controller;

import com.project.wallet_service.exception.WalletBadRequest;
import com.project.wallet_service.exception.WalletNotFoundException;
import com.project.wallet_service.model.Wallet;
import com.project.wallet_service.service.WalletService;
import com.project.wallet_service.util.WalletValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @ApiOperation(value = "Find all the wallets")
    @GetMapping("/findAllWallet")
    List<Wallet> findAllWallet() {
        return walletService.findAll();
    }

    // Find a given wallet
    @GetMapping("/wallet/{id}")
    @ApiOperation(value = "Find wallet by Id ")
    Wallet findOneWallet(@ApiParam(value = "Store id of of the point of service to deliver to/collect from", required = true)@PathVariable int id) {
        logger.info("/wallet/{id} called with id "+ id);
        return walletService.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
    }

    // Create a new wallet
    @PostMapping("/createNewWallet")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create New Wallet ")
    Wallet CreateNewWallet(@RequestBody Wallet newWallet) {
        if(!WalletValidator.validateWalletRequest(newWallet)){
            logger.info("CreateNewWallet request not valid");
            throw  new WalletBadRequest();
        }
        return walletService.save(newWallet);

    }

    // Update a wallet
    @PutMapping("/updateWallet")
    @ApiOperation(value = "Update Wallet ")
    Wallet updateWallet(@RequestBody Wallet newWallet) {
        Wallet wallet = walletService.save(newWallet);
        return wallet;
    }
}
