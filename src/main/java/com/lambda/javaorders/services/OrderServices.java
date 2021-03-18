package com.lambda.javaorders.services;

import com.lambda.javaorders.models.Order;

public interface OrderServices
{
    Order save(Order order);

    Order findOrderById(long orderid);

    void delete(long orderid);
}
