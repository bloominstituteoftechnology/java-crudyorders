package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderServices;
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
    @GetMapping(value = "/order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findByOrdnum(@PathVariable long ordnum)
    {
        Order o = orderServices.findOrderByOrdnum(ordnum);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
    //    POST http://localhost:2019/orders/order
    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid
                                         @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("{ordnum}")
            .buildAndExpand(newOrder.getOrdnum())
            .toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
    }

    //    PUT http://localhost:2019/orders/order/63
    //    DELETE http://localhost:2019/orders/order/58
    @DeleteMapping(value = "order/{ordnum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordnum)
    {
        orderServices.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
