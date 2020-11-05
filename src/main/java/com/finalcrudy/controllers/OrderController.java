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

    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Restaurant newOrder) {
        newOrder.setOrderid(0);
        newOrder = orderServices.save(newOrder);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRestaurantURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{custid}")
            .buildAndExpand(newOrder.getOrderid())
            .toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{ordernum}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> replaceOrderByNum(@PathVariable long ordernum, @Valid @RequestBody Restaurant updateOrder) {
        updateOrder.setOrderid(ordernum);
        updateOrder = orderServices.save(updateOrder);
        return new ResponseEntity<>(updateRestaurant, HttpStatus.OK);
    }

    @DeleteMapping(value = "/order/{ordername}")
    public ResponseEntity<?> deleteOrderByName(@PathVariable String ordername) {
        orderServices.delete(ordername);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
