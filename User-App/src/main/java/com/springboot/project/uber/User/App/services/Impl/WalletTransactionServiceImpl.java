package com.springboot.project.uber.User.App.services.Impl;

import com.springboot.project.uber.User.App.dto.WalletTransactionDto;
import com.springboot.project.uber.User.App.entities.WalletTransaction;
import com.springboot.project.uber.User.App.repositories.WalletTransactionRepository;
import com.springboot.project.uber.User.App.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;


    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }
}