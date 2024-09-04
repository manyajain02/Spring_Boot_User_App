package com.springboot.project.uber.User.App.strategies.Impl;

import com.springboot.project.uber.User.App.entities.Driver;
import com.springboot.project.uber.User.App.entities.Payment;
import com.springboot.project.uber.User.App.entities.Rider;
import com.springboot.project.uber.User.App.entities.enums.PaymentStatus;
import com.springboot.project.uber.User.App.entities.enums.TransactionMethod;
import com.springboot.project.uber.User.App.repositories.PaymentRepository;
import com.springboot.project.uber.User.App.services.WalletService;
import com.springboot.project.uber.User.App.strategies.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
//    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    @Override
    @Transactional
    public void processPayment(Payment payment) {

        Driver driver =  payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(),null, payment.getRide(), TransactionMethod.Ride);

        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);
        walletService.addMoneyToWallet(driver.getUser(), driversCut,null, payment.getRide(), TransactionMethod.Ride);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}
