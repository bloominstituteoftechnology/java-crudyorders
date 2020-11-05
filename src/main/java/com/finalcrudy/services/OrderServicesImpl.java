package com.finalcrudy.services;

import com.finalcrudy.models.Order;
import com.finalcrudy.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Transactional
@Service(value = "ordersService")
public class OrderServicesImpl
    implements OrderService
{
    @Autowired
    private OrderRepository ordersrepos;

    @Override
    public List<Order> findOrdersWithAdvanceAmount()
    {
        return ordersrepos.findAllByAdvanceamountGreaterThan(0.00);
    }

    @Override
    public Order findOrdersById(long id) throws
                                         EntityNotFoundException
    {
        return ordersrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found"));
    }
}