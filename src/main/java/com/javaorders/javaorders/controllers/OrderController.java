package com.javaorders.javaorders.controllers;

import com.javaorders.javaorders.models.Order;
import com.javaorders.javaorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    private OrderServices orderServices;

    // http://localhost:2019/orders/order/{ordnum}
    // Order numbers start at 42
    @GetMapping(value = "/order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findAgentById(@PathVariable long ordnum) {
        Order order = orderServices.findOrderById(ordnum);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
