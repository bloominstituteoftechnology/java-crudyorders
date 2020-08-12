package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.services.CustomerServices;
import com.lambdaschool.crudyorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    // http://localhost:2019/customers/orders - Returns all customers with their orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers()
    {
        List<Customer> myList = customerServices.findAllCustomers();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/{id} - Returns the customer and their orders with the given customer id
    @GetMapping(value = "/customer/{custcode}", produces = "application/json")
    public ResponseEntity<?> findByCustomerCode(@PathVariable long custcode)
    {
        Customer myCustomer = customerServices.findByCustomerCode(custcode);
        return new ResponseEntity<>(myCustomer, HttpStatus.OK);
    }

    // http://localhost:2019/customers/namelike/{likename} - Returns all customers and their orders with a customer name containing the given substring
    @GetMapping(value = "/namelike/{custname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByNameLike(@PathVariable String custname)
    {
        List<Customer> myList = customerServices.findAllCustomersByNameLike(custname);
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2019/customers/orders/count - Using a custom query, return a list of all customers with the number of orders they have placed.
    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?> getOrderCount()
    {
        List<OrderCounts> myList = customerServices.getOrderCounts();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // POST http://localhost:2019/customers/customer - Adds a new customer including any new orders
    @PostMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer) // method header - full json object is coming in, jackson converts json customer to java obj
    {
        newCustomer.setCustcode(0); // if client sends an id, set it to nothing (0)
        newCustomer = customerServices.save(newCustomer); // sends new customer obj and captured ids

        // return in the header location of the new restaurant obj (returning to client)
        // location => http://localhost:2019/customers/customer/10
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequestUri() // grabbed the current request (http://localhost:2019/customers/customer)
            .path("/" + newCustomer.getCustcode())
            .build()
            .toUri(); //convert to true URI
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
        // everything gets stored in the save method in servicesimpl
    }

    // PUT http://localhost:2019/customers/customer/{custcode} - completely replaces the customer record including associated orders with the provided data
    @PutMapping(value = "/customer/{custcode}", consumes = "application/json")
    public ResponseEntity<?> updateFullCustomer(@Valid @RequestBody Customer updateCustomer,
                                                @PathVariable long custcode)
    {
        updateCustomer.setCustcode(custcode);
        customerServices.save(updateCustomer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // PATCH http://localhost:2019/customers/customer/{custcode} - updates customers with the new data. Only the new data is to be sent from the frontend client.


    // DELETE http://localhost:2019/customers/customer/{custcode} - Deletes the given customer including any associated orders
    @DeleteMapping(value = "/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode)
    {
        customerServices.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
