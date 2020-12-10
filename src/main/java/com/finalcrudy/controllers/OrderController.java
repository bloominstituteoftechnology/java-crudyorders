package com.finalcrudy.controllers;

import com.finalcrudy.models.Order;
import com.finalcrudy.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController
{

    @Autowired
    OrderServices orderService;

    @GetMapping(value = "/advanceamount")
    public ResponseEntity<?> getOrdersWithAdvanceAmount()
    {
        List<Order> myOrderList = orderService.findOrdersWithAdvanceAmount();
        return new ResponseEntity<>(myOrderList,
            HttpStatus.OK);
    }

    @GetMapping(value = "/order/{ordernum}",
        produces = {"application/json"})
    public ResponseEntity<?> getOrderByNumber(
        @PathVariable
            long ordernum)
    {
        Order o = orderService.findOrdersById(ordernum);
        return new ResponseEntity<>(o,
            HttpStatus.OK);
    }

    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Order newOrder) {
        newOrder.setOrdnum(0);
        newOrder = orderService.save(newOrder);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{custcode}")
            .buildAndExpand(newOrder.getOrdnum())
            .toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{ordnum}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> replaceOrderByNum(@PathVariable long ordnum, @Valid @RequestBody Order updateOrder) {
        updateOrder.setOrdnum(ordnum);
        updateOrder = orderService.save(updateOrder);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    @DeleteMapping(value = "/order/{ordnum}")
    public ResponseEntity<?> deleteOrderByName(@PathVariable long ordnum) {
        orderService.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
