package com.finalcrudy.controllers;

import com.finalcrudy.models.Customer;
import com.finalcrudy.services.CustomerServices;
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
@RequestMapping("/customers")
public class CustomerController
{
    @Autowired
    private CustomerServices customerService;

    @GetMapping(value = "/orders",
        produces = {"application/json"})
    public ResponseEntity<?> listAllCustomers()
    {
        List<Customer> myCustomers = customerService.findAllCustomers();
        return new ResponseEntity<>(myCustomers,
            HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{custid}",
        produces = {"application/json"})
    public ResponseEntity<?> getCustomerById(
        @PathVariable
            long custid)
    {
        Customer c = customerService.findCustomersById(custid);
        return new ResponseEntity<>(c,
            HttpStatus.OK);
    }

    @GetMapping(value = "/namelike/{custname}",
        produces = {"application/json"})
    public ResponseEntity<?> findCustomerByName(
        @PathVariable
            String custname)
    {
        List<Customer> myCustomerList = customerService.findByCustomerName(custname);
        return new ResponseEntity<>(myCustomerList,
            HttpStatus.OK);
    }

    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customer newCustomer)
    {
        newCustomer.setCustomerid(0);
        newCustomer = customerService.save(newCustomer);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{custid}")
            .buildAndExpand(newCustomer.getCustomerid())
            .toUri();
        responseHeaders.setLocation(newCustomerURI);
        return new ResponseEntity<>(newCustomer, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/{custcode}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> replaceCustomerByCode(@PathVariable long custcode, @Valid @RequestBody Customer updateCustomer) {
        updateCustomer.setCustomercode(custcode);
        updateCustomer = customerService.save(updateCustomer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @PatchMapping(value = "customer/{custcode}", consumes = "application/json")
    public ResponseEntity<?> updateCustomerByCode(@PathVariable long custcode, @RequestBody Restaurant updateCustomer) {
        customerService.update(updateCustomer, custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerByCode(@PathVariable long custcode) {
        customerService.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
