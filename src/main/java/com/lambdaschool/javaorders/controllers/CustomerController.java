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
public class CustomerController {

    @Autowired
    private CustomerServices customerServices;

    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> findAllOrders() {
        List<Customer> rtnList = customerServices.findAllOrders();
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{custcode}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long custcode) {
        Customer rtnCust = customerServices.findCustomerById(custcode);
        return new ResponseEntity<>(rtnCust, HttpStatus.OK);
    }

    @GetMapping(value = "/namelike/{subname}", produces = "application/json")
    public ResponseEntity<?> findCustomersLikeName(@PathVariable String subname) {
        List<Customer> rtnList = customerServices.findByNameLike(subname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    @PostMapping(value = "/customer", produces = "application/json")
    public ResponseEntity<?> addNewCustomer(
            @Valid
            @RequestBody
                    Customer newCustomer) {
        newCustomer.setCustcode(0);
        newCustomer = customerServices.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custcode}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/{custcode}", consumes = "application/json")
    public ResponseEntity<?> updateEntireCustomer(
            @Valid
            @RequestBody
                    Customer updateEntireCustomer,
            @PathVariable
                    long custcode)
    {
        updateEntireCustomer.setCustcode(custcode);
        customerServices.save(updateEntireCustomer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/customer/{custcode}", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(
            @RequestBody
                    Customer updateCustomer,
            @PathVariable
                    long custcode)
    {
        customerServices.update(updateCustomer, custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(
            @PathVariable
                long custcode)
    {
        customerServices.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
