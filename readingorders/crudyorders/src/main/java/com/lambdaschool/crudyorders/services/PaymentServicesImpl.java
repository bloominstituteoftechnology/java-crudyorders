package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service
public class PaymentServicesImpl implements PaymentServices {

  @Autowired
  PaymentRepository paymentrepos;

  @Transactional
  @Override
  public Payment save(Payment payment) {
    return paymentrepos.save(payment);
  }

  @Override
  public Payment findPaymentById(long id) {
    return paymentrepos.findById(id)
                     .orElseThrow(()-> new
                         EntityNotFoundException(
                             "Payment " + id + " not found!"));
          }
}
