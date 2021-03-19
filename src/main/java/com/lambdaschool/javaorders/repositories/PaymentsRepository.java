package com.lambdaschool.javaorders.repositories;

import com.lambdaschool.javaorders.models.Payments;
import org.springframework.data.repository.CrudRepository;

public interface PaymentsRepository extends CrudRepository<Payments, Long> {
}
