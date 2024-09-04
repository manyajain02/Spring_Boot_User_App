package com.springboot.project.uber.User.App.dto;

import com.springboot.project.uber.User.App.entities.Ride;
import com.springboot.project.uber.User.App.entities.Wallet;
import com.springboot.project.uber.User.App.entities.enums.TransactionMethod;
import com.springboot.project.uber.User.App.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class WalletTransactionDto {

    private Long id;

    private Double balance;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    @OneToOne
    private RideDto ride;

    private String transactionId;

    @ManyToOne
    private WalletDto wallet;

    @CreationTimestamp
    private LocalDateTime timeStamp;

}


