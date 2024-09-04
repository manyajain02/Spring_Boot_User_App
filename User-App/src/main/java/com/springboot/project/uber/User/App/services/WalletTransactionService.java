package com.springboot.project.uber.User.App.services;

import com.springboot.project.uber.User.App.dto.WalletTransactionDto;
import com.springboot.project.uber.User.App.entities.WalletTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransaction walletTransaction);


}
