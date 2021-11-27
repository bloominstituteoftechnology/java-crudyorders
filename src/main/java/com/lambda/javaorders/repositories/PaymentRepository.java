package com.lambda.javaorders.repositories;

import com.lambda.javaorders.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long>
{
}
