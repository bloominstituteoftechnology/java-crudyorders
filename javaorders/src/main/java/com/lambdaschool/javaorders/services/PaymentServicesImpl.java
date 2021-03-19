package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentServicesImpl implements PaymentServices {
    @Autowired
    PaymentRepository paymentrepos;

    @Override public Payment save(Payment payment)
    {
        return paymentrepos.save(payment);
    }
}
