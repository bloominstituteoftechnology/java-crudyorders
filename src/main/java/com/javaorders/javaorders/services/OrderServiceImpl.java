package com.javaorders.javaorders.services;


import com.javaorders.javaorders.models.Order;
import com.javaorders.javaorders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderServices")
public class OrderServiceImpl implements OrderServices
{
    @Autowired
    private OrdersRepository ordersrepos;

    @Transactional
    @Override
    public Order save(Order order)
    {
        return ordersrepos.save(order);
    }

    @Override
    public Order findOrderById(long ordnum) {
        Order order = ordersrepos.findById(ordnum).orElseThrow(() -> new EntityNotFoundException());
        return order;
    }
}
