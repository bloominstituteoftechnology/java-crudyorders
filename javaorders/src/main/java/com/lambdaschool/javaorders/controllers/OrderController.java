package com.lambdaschool.javaorders.controllers;


import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    private OrderServices orderServices;
//    http://localhost:2019/orders/order/7
    @GetMapping( value = "order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long ordnum)
    {
        Order o = orderServices.findOrderById(ordnum);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

//    POST http://localhost:2019/orders/order - adds a new order to an existing customer
    @PostMapping(value = "/order")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{ordnum}")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
    }

//
//    PUT http://localhost:2019/orders/order/{ordernum} - completely replaces the given order record
//
//    DELETE http://localhost:2019/orders/order/{ordername} - deletes the given order

}

