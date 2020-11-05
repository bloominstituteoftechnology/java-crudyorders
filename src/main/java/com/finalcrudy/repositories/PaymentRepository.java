package com.finalcrudy.repositories;

import com.finalcrudy.models.Payment;
import org.springframework.data.repository.CrudRepository;


public interface PaymentRepository
    extends CrudRepository<Payment, Long>
{
}