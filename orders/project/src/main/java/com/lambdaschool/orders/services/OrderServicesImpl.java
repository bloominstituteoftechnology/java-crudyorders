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
public class OrderServicesImpl implements OrderServices{
  @Autowired
  private OrderRepository orderRepository;

  @Transactional
  @Override
  public Order save(Order order) {
    return orderRepository.save(order);
  }

  @Override
  public Order findOrderById(Long ordernum) throws EntityNotFoundException {
    return orderRepository.findById(ordernum)
        .orElseThrow(() -> new EntityNotFoundException("Order " + ordernum + " not found."));
  }

  @Override
  public List<Order> findOrderWithAdvanceAmount() {
    return orderRepository.findOrderByAdvanceamountGreaterThan(0.0);
  }
}