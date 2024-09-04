package com.springboot.project.uber.User.App.strategies;

import com.springboot.project.uber.User.App.entities.Payment;

public interface PaymentStrategy {

   static final Double PLATFORM_COMMISSION = 0.3;
    void processPayment(Payment payment);
}
