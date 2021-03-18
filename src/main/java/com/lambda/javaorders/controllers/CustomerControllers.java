package com.lambda.javaorders.controllers;

import com.lambda.javaorders.models.Customer;
import com.lambda.javaorders.models.Order;
import com.lambda.javaorders.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerControllers
{
    @Autowired
    private CustomerServices customerServices;
    // list of all customers and orders
    // http://localhost:2019/customers/orders
    @GetMapping(value = "/orders")
    public ResponseEntity<?> listAllOrders()
    {
        List<Customer> listCustomers = customerServices.findAllCustomers();
        return new ResponseEntity<>(listCustomers, HttpStatus.OK);
    }

    // find customer by id number
    // http://localhost:2019/customers/customer/7
    @GetMapping(value = "/customer/{customerid}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long customerid)
    {
        Customer c = customerServices.findCustomerById(customerid);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    // find customer by name
    // http://localhost:2019/customers/namelike/mes
    @GetMapping(value = "/namelike/{subname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByNameLike(@PathVariable String subname)
    {
        List<Customer> rtnList = customerServices.findByNameLike(subname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // POST /customers/customer - Adds a new customer including any new orders


    // PUT /customers/customer/{custcode} - completely replaces the customer record including associated orders with the provided data


    // PATCH /customers/customer/{custcode} - updates customers with the new data. Only the new data is to be sent from the frontend client.


    // DELETE - Deletes the given customer including any associated orders
    // http://localhost:2019/customers/customer/7
    @DeleteMapping(value = "/customer/{custid}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custid)
    {
        customerServices.delete(custid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
