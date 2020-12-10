package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Payment;

public interface PaymentServices
{
    Payment save(Payment payment); // create method called save, means other apps can create a payment type
}
