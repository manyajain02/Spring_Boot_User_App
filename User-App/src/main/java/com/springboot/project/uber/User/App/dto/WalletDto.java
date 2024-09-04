package com.springboot.project.uber.User.App.dto;

import com.springboot.project.uber.User.App.entities.User;
import com.springboot.project.uber.User.App.entities.WalletTransaction;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.List;

@Data
public class WalletDto {
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private Double balance = 0.0;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)
    private List<WalletTransaction> transactions;
}
