package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;

import java.util.List;

public interface OrderServices {

  Order findOrderById(Long ordernum);

  List<Order> findOrderWithAdvanceAmount();

  Order save(Order order);

  Order update(long id, Order receivedOrder);

  void delete(long id);
}
