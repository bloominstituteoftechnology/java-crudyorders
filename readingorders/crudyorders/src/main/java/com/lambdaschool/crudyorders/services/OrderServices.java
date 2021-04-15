package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;

import java.util.List;

public interface OrderServices {

  List<Order> findAllOrders();

  Order findOrderById(long id);

  Order save(Order order);

  Order update(Order order, long id);

  void delete(long id);
}
