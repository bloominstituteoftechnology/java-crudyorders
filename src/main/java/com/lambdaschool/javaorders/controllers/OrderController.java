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
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping(value = "/order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long ordnum)
    {
        Order rtnOrder = orderServices.findOrderById(ordnum);
        return new ResponseEntity<>(rtnOrder, HttpStatus.OK);
    }

    @PostMapping(value = "/order", produces = "application/json")
    public ResponseEntity<?> addNewOrder(
            @Valid
            @RequestBody
                    Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ordnum}")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{ordnum}", consumes = "application/json")
    public ResponseEntity<?> updateFullOrder(
            @Valid
            @RequestBody
                    Order updateOrder,
            @PathVariable
                    long ordnum){
        updateOrder.setOrdnum(ordnum);
        orderServices.update(updateOrder, ordnum);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/order/{ordnum}")
    public ResponseEntity<?> deleteOrderById(
            @PathVariable
                    long ordnum){
        orderServices.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
