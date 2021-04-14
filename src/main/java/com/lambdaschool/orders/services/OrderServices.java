package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.views.OrderCounts;

import java.util.List;

public interface OrderServices
{
    Order save(Order order);

    Order findOrderByOrdnum(long ordnum);


    void delete(long ordnum);
}
