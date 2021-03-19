package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices {
    @Autowired
    OrdersRepository orderrepos;

    @Transactional
    // anytime you are saving, transactional
    @Override public Order save(Order order)
    {
        Order newOrder = new Order();
        if (order.getOrdnum() !=0 )
        {
            orderrepos.findById(order.getOrdnum())
                .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found"));
            newOrder.setOrdnum(order.getOrdnum());
        }
// Validation
        newOrder.setCustomer(order.getCustomer());
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        return orderrepos.save(newOrder);
    }

    @Override
    public Order findOrderById(long ordnum) {
        return orderrepos.findById(ordnum)
        .orElseThrow(() -> new EntityNotFoundException("Order #" + ordnum + " not found!"));
    }
}
