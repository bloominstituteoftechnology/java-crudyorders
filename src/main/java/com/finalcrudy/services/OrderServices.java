package com.finalcrudy.services;

import com.finalcrudy.models.Order;

import java.util.List;

public interface OrderServices
{

    List<Order> findOrdersWithAdvanceAmount();

    Order findOrdersById(long id);
}