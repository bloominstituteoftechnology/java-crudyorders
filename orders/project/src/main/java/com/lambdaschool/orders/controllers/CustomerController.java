package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.CustomerServices;
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
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  private CustomerServices customerServices;

  //http://localhost:2019/customers/orders
  @GetMapping(value = "/orders", produces="application/json")
  public ResponseEntity<?> findAllOrders() {
    List<Customer> customerList = customerServices.findAllCustomers();
    return new ResponseEntity<>(customerList, HttpStatus.OK);
  }

  //http://localhost:2019/customers/customer/{custcode}
  //http://localhost:2019/customers/customer/{custcode}
  @GetMapping(value = "/customer/{custid}", produces = "application/json")
  public ResponseEntity<?> getCustomerById(@PathVariable long custid) {
    Customer c = customerServices.findCustomerById(custid);
    return new ResponseEntity<>(c, HttpStatus.OK);
  }


  //http://localhost:2019/customers/namelike/mes
  //http://localhost:2019/customers/namelike/cin
  @GetMapping(value = "/namelike/{substring}", produces = "application/json")
  public ResponseEntity<?> findCustomerByNameLike(@PathVariable String substring) {
    List<Customer> customers = customerServices.findByNameLike(substring);
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @DeleteMapping(value = "/customer/{id}")
  public ResponseEntity<?> deleteCustomerById(@PathVariable long id) {
    customerServices.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer customer) {
    customer.setCustcode(0);
    customer = customerServices.save(customer);

    HttpHeaders responseHeaders = new HttpHeaders();
    URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(customer.getCustcode())
        .toUri();
    responseHeaders.setLocation(newCustomerURI);

    return new ResponseEntity<>(customer, responseHeaders, HttpStatus.CREATED);
  }

  @PutMapping(value = "/customer/{id}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> replaceCustomer(@Valid @RequestBody Customer customer, @PathVariable long id) {
    customer.setCustcode(id);
    customer = customerServices.save(customer);
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }

  @PatchMapping(value = "/customer/{id}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable long id) {
    customer = customerServices.update(id, customer);
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }

}

