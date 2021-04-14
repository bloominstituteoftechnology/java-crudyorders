package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;

import java.util.List;

public interface OrderServices {


        List findAllOrders();

        Order findOrderById(long id);
    }


