package com.lambda.javaorders.services;

import com.lambda.javaorders.models.Order;
import com.lambda.javaorders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    OrderRepository orderrepos;

    @Transactional
    @Override
    public Order save(Order order) {
        return orderrepos.save(order);
    }

    @Override
    public Order findOrderById(long orderid)
    {
        return orderrepos.findById(orderid)
            .orElseThrow(() -> new EntityNotFoundException("Order " + orderid + " Not Found"));
    }

    @Transactional
    @Override
    public void delete(long orderid)
    {
        if (orderrepos.findById(orderid).isPresent())
        {
            orderrepos.deleteById(orderid);
        } else
        {
            throw new EntityNotFoundException("Order " + orderid + " Not Found");
        }
    }
}
