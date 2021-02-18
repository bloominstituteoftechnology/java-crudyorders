package com.javaorders.javaorders.services;

import com.javaorders.javaorders.models.Order;


public interface OrderServices
{
    public Order save(Order order);

    Order findOrderById(long ordnum);
}
