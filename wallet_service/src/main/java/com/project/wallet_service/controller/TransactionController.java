package com.project.wallet_service.controller;

import com.project.wallet_service.exception.TransactionBadRequest;
import com.project.wallet_service.model.AddBalanceDetails;
import com.project.wallet_service.model.Transaction;
import com.project.wallet_service.model.User;
import com.project.wallet_service.model.Wallet;
import com.project.wallet_service.service.EmailService;
import com.project.wallet_service.service.TransactionService;
import com.project.wallet_service.service.UserService;
import com.project.wallet_service.service.WalletService;
import com.project.wallet_service.util.TransactionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


@RestController
public class TransactionController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);


    //Send money from one user to another
    @PostMapping("/sendMoney")
    @ResponseStatus(HttpStatus.CREATED)
    Transaction sendMoney(@RequestBody Transaction transaction) throws Exception {

        if(!TransactionValidator.validateRequest(transaction)){
            throw new TransactionBadRequest();
        }

        transaction.setDate(new Date(Calendar.getInstance().getTime().getTime()));

        User sender = userService.getAUser(String.valueOf(transaction.getSid()));
        User receiver = userService.getAUser(String.valueOf(transaction.getRid()));

        if(sender==null || receiver==null){
            logger.info("No wallet for sender or receiver");
            throw new TransactionBadRequest();
        }

        Wallet senderWallet = walletService.findWalletByUserId(sender.getId());
        Wallet receiverWallet = walletService.findWalletByUserId(receiver.getId());

        int amt = transaction.getAmount();

        if (senderWallet.getBalance() < amt) {
            throw new Exception("Not Sufficient Balance");
        }
        senderWallet.setBalance(senderWallet.getBalance()-amt);

        receiverWallet.setBalance(receiverWallet.getBalance()+amt);

        transaction.setStatus("SUCCESS");
        logger.info(String.format("$$ -> Producing Transaction --> %s", transaction));
        walletService.save(receiverWallet);
        walletService.save(senderWallet);
        return transactionService.save(transaction);
    }


    //get Balance by given id
    @GetMapping("/getBal/{id}")
    int getBal(@PathVariable int id) throws Exception {

        Wallet wallet = walletService.findWalletByUserId(id);

        if(wallet==null) throw new Exception("Wallet Not Found");
        else {
            return wallet.getBalance();
        }
    }


    //To add balance in wallet
    @PostMapping("/addBalance")
    AddBalanceDetails addBalance(@RequestBody AddBalanceDetails request){

        logger.info(request.toString());
        Wallet wallet = walletService.findWalletByUserId(request.getUid());

        wallet.setBalance(request.getAmount()+wallet.getBalance());
        walletService.save(wallet);
        return request;
    }


    //To get transaction history by given id
    @GetMapping("/txnHistory/{id}")
    String getTransactionHistory(@PathVariable int id) {
        logger.info(String.format("$$ -> Producing Transaction --> %s", id));
        sendTxnHistory(String.valueOf(id));
        return "You will get the file on your email";
    }

    //method called
    private void sendTxnHistory(String id) {
        int id1 = Integer.parseInt(id);
        ArrayList<Transaction> list =
                (ArrayList<Transaction>) transactionService.findBySidOrRid(id1);

        User user1 = userService.getAUser(String.valueOf(id1));

        String filename ="test.csv";
        try {
            FileWriter fw = new FileWriter(filename);

            for(int i=0;i<list.size();i++) {
                fw.append(list.get(i).getStatus());
                fw.append(',');
                int amt = list.get(i).getAmount();
                Integer obj = amt;
                fw.append(obj.toString());
                fw.append(',');
                fw.append(list.get(i).getDate().toString());
                fw.append(',');
                int id2 = list.get(i).getId();
                Integer obj2 = id2;
                fw.append(obj2.toString());
                fw.append(',');
                int rid = list.get(i).getRid();
                obj = rid;
                fw.append(obj.toString());
                fw.append(',');
                int sid = list.get(i).getSid();
                obj = sid;
                fw.append(obj.toString());
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            logger.info("CSV File is created successfully.");
            EmailService.sendEmailWithAttachments("toAddress@gmail.com",filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




