package com.finalcrudy.controllers;

import com.finalcrudy.models.Order;
import com.finalcrudy.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController
{

    @Autowired
    OrderServices orderService;

    @GetMapping(value = "/advanceamount")
    public ResponseEntity<?> getOrdersWithAdvanceAmount()
    {
        List<Order> myOrderList = ordersService.findOrdersWithAdvanceAmount();
        return new ResponseEntity<>(myOrderList,
            HttpStatus.OK);
    }

    @GetMapping(value = "/order/{ordernum}",
        produces = {"application/json"})
    public ResponseEntity<?> getOrderByNumber(
        @PathVariable
            long ordernum)
    {
        Order o = ordersService.findOrdersById(ordernum);
        return new ResponseEntity<>(o,
            HttpStatus.OK);
    }
}
