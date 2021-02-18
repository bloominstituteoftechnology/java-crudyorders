package com.javaorders.javaorders.controllers;

import com.javaorders.javaorders.models.Customer;
import com.javaorders.javaorders.services.CustomerServices;
import com.javaorders.javaorders.views.OrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController
{
    @Autowired
    private CustomerServices customerServices;

    //http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomersOrders() {
        List<Customer> customerOrderList = customerServices.findAllCustomersOrders();
        return new ResponseEntity<>(customerOrderList, HttpStatus.OK);
    }

    //http://localhost:2019/customers/customer/{custcode}
    // customer custcode starts at 17
    @GetMapping(value = "/customer/{custcode}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long custcode) {
        Customer customer = customerServices.findCustomerById(custcode);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    //http://localhost:2019/customers/namelike/{likename}
    @GetMapping(value = "/namelike/{likename}", produces = "application/json")
    public ResponseEntity<?> findCustomerByLikeName(@PathVariable String likename) {
        List<Customer> customerList = customerServices.findCustomerByLikeName(likename);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    //http://localhost:2019/customers/orders/count
    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?> getOrderCount() {
        List<OrderCount> orderCount = customerServices.getOrderCount();
        return new ResponseEntity<>(orderCount, HttpStatus.OK);
    }
}
