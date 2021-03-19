package com.lambdaschool.javaorders.services;


import com.lambdaschool.javaorders.models.Payments;
import com.lambdaschool.javaorders.repositories.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "paymentsservice")
public class PaymentsServiceImpl implements PaymentsService {
    @Autowired
    PaymentsRepository paymentrepo;

    @Transactional
    @Override
    public Payments save(Payments payments) {
        return paymentrepo.save(payments);
    }

    @Override
    public void deleteAllPayments() {
        paymentrepo.deleteAll();
    }
}
