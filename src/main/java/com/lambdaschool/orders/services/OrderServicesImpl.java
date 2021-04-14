package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    private OrderRepository orderrepos;
    @Transactional
    @Override
    public Order save(Order order)
    {
        return orderrepos.save(order);
    }

    @Override
    public Order findOrderByOrdnum(long ordnum)
    {
        return orderrepos.findById(ordnum)
            .orElseThrow(() -> new EntityNotFoundException("Order " + ordnum + " Not Found"));
    }

}
