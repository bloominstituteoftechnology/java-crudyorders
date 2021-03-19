package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Payments;

public interface PaymentsService {
    Payments save(Payments payments);

    void deleteAllPayments();
}
