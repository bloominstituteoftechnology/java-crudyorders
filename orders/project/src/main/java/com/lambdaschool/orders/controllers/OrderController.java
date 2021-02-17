package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  private OrderServices orderServices;


  //<summary>http://localhost:2019/orders/order/7</summary>
  @GetMapping(value = "/order/{ordernum}", produces = "application/json")
  public ResponseEntity<?> getOrderById(@PathVariable Long ordernum) {
    Order o = orderServices.findOrderById(ordernum);
    return new ResponseEntity<>(o, HttpStatus.OK);
  }

  ///orders/advanceamount
  @GetMapping(value = "/advanceamount", produces = "application/json")
  public ResponseEntity<?> findOrdersWithAdvanceAmount(){
    List<Order> orders = orderServices.findOrderWithAdvanceAmount();
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

}
