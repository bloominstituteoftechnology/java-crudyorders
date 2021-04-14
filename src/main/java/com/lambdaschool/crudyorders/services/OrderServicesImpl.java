package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Service(value="orderservice")
public class OrderServicesImpl implements OrderServices {

    @Autowired
    private OrderRepository orderrepos;


    @Override
    public List<Order> findAllOrders() {

        List<Order> list = new ArrayList<>();
        orderrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Order findOrderById(long id) throws EntityNotFoundException {
        return orderrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order" + id + "not found"));

    }
}
