package com.lambda.javaorders.controllers;

import com.lambda.javaorders.models.Order;
import com.lambda.javaorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderControllers
{
    @Autowired
    private OrderServices orderServices;
    // Returns a single order
    // http://localhost:2019/orders/order/7
    @GetMapping(value = "/order/{orderid}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long orderid)
    {
        Order o = orderServices.findOrderById(orderid);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
}
