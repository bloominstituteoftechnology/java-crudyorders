package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.OrderServices;
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

    // http://localhost:2019/orders/order/{id} - Returns the order and its customer with the given order number
    @GetMapping(value = "/order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findByOrderNum(@PathVariable long ordnum)
    {
        Order myOrder = orderServices.findByOrderNum(ordnum);
        return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }

    // POST http://localhost:2019/orders/order - adds a new order to an existing customer
    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);

        // return in the header location of the new restaurant obj (returning to client)
        // location => http://localhost:2019/orders/order/{ordnum}
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequestUri() // grabbed the current request
            .path("/" + newOrder.getOrdnum())
            .build()
            .toUri(); //convert to true URI
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
        // everything gets stored in the save method in servicesimpl
    }

    // PUT http://localhost:2019/orders/order/{ordnum} - completely replaces the given order record
    @PutMapping(value = "/order/{ordnum}", consumes = "application/json")
    public ResponseEntity<?> updateFullRestaurant(@Valid @RequestBody Order updateOrder,
                                                  @PathVariable long ordnum)
    {
        updateOrder.setOrdnum(ordnum);
        orderServices.save(updateOrder);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE http://localhost:2019/orders/order/{ordnum} - deletes the given order
    @DeleteMapping(value = "/order/{ordnum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordnum)
    {
        orderServices.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
