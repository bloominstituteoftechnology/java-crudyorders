package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping(value = "order/{orderid}", produces = "application/json")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderid){
        Order o = orderServices.findOrderById(orderid);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
}
