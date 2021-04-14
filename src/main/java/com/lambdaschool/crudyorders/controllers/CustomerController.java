package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class CustomerController {
    @Autowired
    private CustomerServices customerServices;

    @GetMapping(value = "/customers/customer/{custid}",produces = "application/json")
    public ResponseEntity<?> getCustomerById(@PathVariable Long custid){
        Customer c = customerServices.findCustomerById(custid);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @GetMapping(value = "/customers/namelike/{custname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByNameLike(@PathVariable String custname){
        List<Customer> rtnList = customerServices.findByNameLike(custname);
        return new ResponseEntity<>(rtnList,HttpStatus.OK);
    }

    @GetMapping(value = "/customers/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers() {
        List<Customer> custList = customerServices.findAllCustomers();
        return new ResponseEntity<>(custList,
                HttpStatus.OK);
    }

}
