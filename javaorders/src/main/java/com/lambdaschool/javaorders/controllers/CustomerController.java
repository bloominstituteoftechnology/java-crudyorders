package com.lambdaschool.javaorders.controllers;


import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController
{
    @Autowired
    private CustomerServices customerServices;

//    http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers()
    {
        List<Customer> listCust = customerServices.findAllCustomers();
        return new ResponseEntity<>(listCust, HttpStatus.OK);
    }

//    http://localhost:2019/customers/customer/7
//    http://localhost:2019/customers/customer/77
    @GetMapping(value = "/customer/{custid}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long custid)
    {
        Customer c = customerServices.findCustomerById(custid);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

//    http://localhost:2019/customers/namelike/mes
//    http://localhost:2019/customers/namelike/cin
    @GetMapping( value = "/namelike/{subname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByNameLike(@PathVariable String subname)
    {
        List<Customer> rtnList = customerServices.findByNameLike(subname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

//    POST http://localhost:2019/customers/customer Adds a new customer including any new orders
    @PostMapping(value = "/customer")
    public ResponseEntity<?> addRestaurant(@Valid @RequestBody Customer newCustomer)
    {
        newCustomer.setCustomerid(0);
        newCustomer = customerServices.save(newCustomer);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{customerid}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);
        return new ResponseEntity<>(newCustomer, responseHeaders, HttpStatus.CREATED);
    }
//
//    PUT http://localhost:2019/customers/customer/{custcode} - completely replaces the customer record including associated orders with the provided data
    @PutMapping(value = "customer/{custcode}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateFullCustomer(@Valid @RequestBody Customer updateCustomer, @PathVariable long custcode)
    {
        updateCustomer.setCustcode(custcode);
        updateCustomer = customerServices.save(updateCustomer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

//    PATCH http://localhost:2019/customers/customer/{custcode} - updates customers with the new data. Only the new data is to be sent from the frontend client.
    @PatchMapping(value = "customer/{custcode}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer updateCustomer, @PathVariable long custcode)
    {
        updateCustomer = customerServices.update(updateCustomer, custcode);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }
//     DELETE http://localhost:2019/customers/customer/{custcode} - Deletes the given customer including any associated orders
    @DeleteMapping(value = "/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode)
    {
        customerServices.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
