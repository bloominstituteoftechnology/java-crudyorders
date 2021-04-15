package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.views.OrderCounts;

import java.util.List;

public interface OrderServices
{
    Order save(Order order);
    Order findOrderByOrdnum(long ordnum);
    Order update(Order order, long id);
    void delete(long ordnum);
}
