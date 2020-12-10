package com.finalcrudy.services;

import com.finalcrudy.models.Order;

import java.util.List;

public interface OrderServices
{

    List<Order> findOrdersWithAdvanceAmount();


    Order findOrdersById(long id);

    Order save(Order order); //POST

    void delete(long ordername); //DELETE

    Order update(Order order, long ordnum); //PATCH vs PUT

    void deleteAll();
}
