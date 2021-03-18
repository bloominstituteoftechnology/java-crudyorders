package com.lambda.javaorders.services;

import com.lambda.javaorders.models.Payment;
import com.lambda.javaorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "paymentServices")
public class PaymentServicesImpl implements PaymentServices
{
    @Autowired
    PaymentRepository payrepos;

    @Transactional
    @Override
    public Payment save(Payment payment) {
        return payrepos.save(payment);
    }
}
