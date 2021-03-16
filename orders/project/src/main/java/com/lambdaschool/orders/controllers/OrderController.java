package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  private OrderServices orderServices;


  //<summary>http://localhost:2019/orders/order/7</summary>
  @GetMapping(value = "/order/{ordernum}", produces = "application/json")
  public ResponseEntity<?> getOrderById(@PathVariable Long ordernum) {
    Order o = orderServices.findOrderById(ordernum);
    return new ResponseEntity<>(o, HttpStatus.OK);
  }

  ///orders/advanceamount
  @GetMapping(value = "/advanceamount", produces = "application/json")
  public ResponseEntity<?> findOrdersWithAdvanceAmount(){
    List<Order> orders = orderServices.findOrderWithAdvanceAmount();
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  //http://localhost:2019/orders/order/{id}
  @DeleteMapping(value = "/order/{id}")
  public ResponseEntity<?> deleteOrderById(@PathVariable long id) {
    orderServices.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  //http://localhost:2019/orders/order
  @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order order) {
    order.setOrdnum(0);
    order = orderServices.save(order);

    HttpHeaders responseHeaders = new HttpHeaders();
    URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{orderid}")
        .buildAndExpand(order.getOrdnum())
        .toUri();
    responseHeaders.setLocation(newOrderURI);

    return new ResponseEntity<>(order, responseHeaders, HttpStatus.CREATED);
  }

  //http://localhost:2019/orders/order/{id}
  @PutMapping(value = "/order/{id}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> replaceOrder(@Valid @RequestBody Order order, @PathVariable long id) {
    order.setOrdnum(id);
    order = orderServices.save(order);

    return new ResponseEntity<>(order, HttpStatus.OK);
  }
}
