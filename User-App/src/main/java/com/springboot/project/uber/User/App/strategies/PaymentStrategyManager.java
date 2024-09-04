package com.springboot.project.uber.User.App.strategies;

import com.springboot.project.uber.User.App.entities.enums.PaymentMethod;
import com.springboot.project.uber.User.App.services.WalletService;
import com.springboot.project.uber.User.App.strategies.Impl.CashPaymentStrategy;
import com.springboot.project.uber.User.App.strategies.Impl.WalletPaymentStrategy;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){

       return switch (paymentMethod) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
         };
    }
}
