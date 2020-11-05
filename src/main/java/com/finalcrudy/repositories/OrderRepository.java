package com.finalcrudy.repositories;

import com.finalcrudy.models.Order;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepository
    extends CrudRepository<Order, Long>
{
}