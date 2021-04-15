package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.services.CustomerServices;
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
public class CustomerController {

    @Autowired
    private CustomerServices customerServices;

    @GetMapping(value = "/customers/customer/{custid}", produces = "application/json")
    public ResponseEntity<?> getCustomerById(@PathVariable Long custid) {
        Customer c = customerServices.findCustomerById(custid);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @GetMapping(value = "/customers/namelike/{custname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByNameLike(@PathVariable String custname) {
        List<Customer> rtnList = customerServices.findByNameLike(custname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    @GetMapping(value = "/customers/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers() {
        List<Customer> custList = customerServices.findAllCustomers();
        return new ResponseEntity<>(custList,
                HttpStatus.OK);
    }
    @DeleteMapping(value="/customers/customer/{custcode}")
    public ResponseEntity<?> deleteById(@PathVariable long custcode){
        customerServices.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping(value ="/customers/customer/{custcode}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> replaceCustomerById(@PathVariable long custcode, @RequestBody @Valid Customer customer){
        customer.setCustcode(custcode);
        Customer newCustomer = customerServices.save(customer);
        return new ResponseEntity<>(newCustomer,HttpStatus.OK);
    }
    @PatchMapping(value = "/customers/customer/{custcode}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateCustomerById (@PathVariable long custcode, @RequestBody Customer customer) {
        Customer updatedCustomer = customerServices.update(custcode, customer);
        return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);


    }

    @PostMapping(value = "/customers/customer", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid Customer newCustomer) {
        newCustomer.setCustcode(0);
        newCustomer = customerServices.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/custcode")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }


}

