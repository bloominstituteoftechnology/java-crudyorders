package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

