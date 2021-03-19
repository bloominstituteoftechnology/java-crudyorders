package com.lambda.javaorders.controllers;

import com.lambda.javaorders.models.Customer;
import com.lambda.javaorders.models.Order;
import com.lambda.javaorders.services.OrderServices;
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

    // PUT - completely replaces the given order record
    // http://localhost:2019/orders/order/7
    @PutMapping(value = "/order/{orderid}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateFullOrder(@Valid @RequestBody Order updateOrder, @PathVariable long orderid)
    {
        updateOrder.setOrdnum(orderid);
        updateOrder = orderServices.save(updateOrder);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    // POST - adds a new order to an existing customer
    // http:localhost:2019/orders/order
    @PostMapping(value = "/order", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);

        // Http Headers - location : link to the newly created customer
        HttpHeaders responseHeaders = new HttpHeaders();
        // http://localhost:2019/orders/order/15
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderid}")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
    }


    // DELETE - deletes the given order
    // http://localhost:2019/orders/order/7
    @DeleteMapping(value = "/order/{order}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long orderid)
    {
        orderServices.delete(orderid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
