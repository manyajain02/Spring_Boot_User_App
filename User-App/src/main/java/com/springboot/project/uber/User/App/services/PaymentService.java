package com.springboot.project.uber.User.App.services;

import com.springboot.project.uber.User.App.entities.Payment;
import com.springboot.project.uber.User.App.entities.Ride;
import com.springboot.project.uber.User.App.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);
}
