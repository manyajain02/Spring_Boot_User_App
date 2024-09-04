package com.springboot.project.uber.User.App.strategies.Impl;

import com.springboot.project.uber.User.App.entities.Driver;
import com.springboot.project.uber.User.App.entities.Payment;
import com.springboot.project.uber.User.App.entities.enums.PaymentStatus;
import com.springboot.project.uber.User.App.entities.enums.TransactionMethod;
import com.springboot.project.uber.User.App.repositories.PaymentRepository;
import com.springboot.project.uber.User.App.services.WalletService;
import com.springboot.project.uber.User.App.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver =  payment.getRide().getDriver();
        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission,null,
                payment.getRide(), TransactionMethod.Ride);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}
