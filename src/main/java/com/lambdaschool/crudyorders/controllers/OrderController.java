package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping(value = "order/{orderid}", produces = "application/json")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderid) {
        Order o = orderServices.findOrderById(orderid);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }
    @PostMapping(value="/order",produces = "application/json",consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@RequestBody @Valid Order order) {
        order.setOrdnum(0);
        Order newOrder = orderServices.save(order);
        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @PutMapping(value = "/order/{ordernum}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> replaceOrderById(@PathVariable long ordernum, @RequestBody @Valid Order order) {
        order.setOrdnum(ordernum);

        Order newOrder = orderServices.save(order);

        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }
    @DeleteMapping(value = "/order/{orderhum}")
    public ResponseEntity<?> deleteById(@PathVariable long orderhum) {
        orderServices.delete(orderhum);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}

