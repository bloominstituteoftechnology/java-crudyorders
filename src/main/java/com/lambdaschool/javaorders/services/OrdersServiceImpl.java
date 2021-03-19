package com.lambdaschool.javaorders.services;


import com.lambdaschool.javaorders.models.Orders;
import com.lambdaschool.javaorders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "ordersservice")
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersRepository ordersrepo;

    @Transactional

    @Override
    public Orders save(Orders orders) {
        return ordersrepo.save(orders);
    }
}
