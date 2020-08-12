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
public class OrderController
{
    @Autowired
    private OrderServices orderServices;

    // http://localhost:2019/orders/order/{id} - Returns the order and its customer with the given order number
    @GetMapping(value = "/order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findByOrderNum(@PathVariable long ordnum)
    {
        Order myOrder = orderServices.findByOrderNum(ordnum);
        return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }
}
