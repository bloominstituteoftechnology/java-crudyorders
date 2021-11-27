package com.lambda.javaorders.repositories;

import com.lambda.javaorders.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>
{
}
