package com.springboot.project.uber.User.App.services;

import com.springboot.project.uber.User.App.entities.Ride;
import com.springboot.project.uber.User.App.entities.User;
import com.springboot.project.uber.User.App.entities.Wallet;
import com.springboot.project.uber.User.App.entities.enums.TransactionMethod;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount, String transactionId,
                            Ride ride, TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    Wallet fidaWalletById(Long Wallet);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);
}
