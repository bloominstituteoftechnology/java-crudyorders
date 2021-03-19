package com.lambdaschool.javaorders.controllers;


import com.lambdaschool.javaorders.models.Customers;
import com.lambdaschool.javaorders.services.CustomersService;
import org.hibernate.annotations.GeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    @Autowired
    CustomersService customersService;

    //http://localhost:2019/customers/orders
    // Returns all customers with their orders
    @GetMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<?> ListAllCustomers()
    {
        List<Customers> rtnList = customersService.findAllCustomers();
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }


    //http://localhost:2019/customers/customer/7
    //http://localhost:2019/customers/customer/77
    //Returns the customer and their orders with the given customer id
    @GetMapping(value = "/customer/{custid}", produces = {"application/json"})
    public ResponseEntity<?> findByCustomerId(@PathVariable long custid)
    {
        Customers rtnCust = customersService.findByCustomerId(custid);
        return new ResponseEntity<>(rtnCust, HttpStatus.OK);
    }


    //http://localhost:2019/customers/namelike/mes
    //http://localhost:2019/customers/namelike/cin
    //Returns all customers and their orders with a customer name containing the given substring
    @GetMapping(value = "/namelike/{subname}", produces = {"application/json"})
    public ResponseEntity<?> findCustomersLikeName(@PathVariable String subname)
    {
        List<Customers> rtnList = customersService.findCustomersByLikeName(subname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }


//http://localhost:2019/agents/agent/9
//Returns the agent and their customers with the given agent id


//http://localhost:2019/orders/order/7
//Returns the order and its customer with the given order number



//POST /customers/customer
//Adds a new customer including any new orders
    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customers newCustomer)
    {
       newCustomer.setCustcode(0); //lets it know that it is a post method
       newCustomer = customersService.save(newCustomer); // we save it and set it to generate a new id
        HttpHeaders responseHeader = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custcode}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
       responseHeader.setLocation(newCustomerURI);
       return new ResponseEntity<>(newCustomer, responseHeader, HttpStatus.CREATED);
    }

// PUT /customers/customer/{custcode}
//completely replaces the customer record including associated orders with the provided data
    @PutMapping(value = "customer/{custcode}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> replaceCustomerById(@PathVariable long custcode, @Valid @RequestBody Customers updateCustomer)
    {
        updateCustomer.setCustcode(custcode);
        updateCustomer = customersService.save(updateCustomer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

//PATCH /customers/customer/{custcode}
//updates customers with the new data. Only the new data is to be sent from the frontend client.
    @PatchMapping(value = "/customer/{custcode}", consumes = "application/json")
    public ResponseEntity<?> updateCustomerById(@PathVariable long custcode, @RequestBody Customers updateCustomer)
    {
        customersService.update(updateCustomer, custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//DELETE /customers/customer/{custcode}
//Deletes the given customer including any associated orders
    @DeleteMapping(value = "/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode)
    {
        customersService.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }



//POST /orders/order
//adds a new order to an existing customer

//PUT /orders/order/{ordernum}
//completely replaces the given order record

//DELETE /orders/order/{ordername}
//deletes the given order
}
