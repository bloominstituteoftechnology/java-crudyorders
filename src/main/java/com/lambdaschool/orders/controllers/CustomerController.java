package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerServices;
import com.lambdaschool.orders.views.AdvanceAmounts;
import com.lambdaschool.orders.views.OrderCounts;
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
    public ResponseEntity<?> listAllOrders()
    {
        List<Customer> rtnList = customerServices.findAllCustomersOrders();
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    //    http://localhost:2019/customers/customer/7
    @GetMapping(value = "/customer/{custcode}", produces = "application/json")
    public ResponseEntity<?> findByCustcode(@PathVariable long custcode)
    {
        Customer c = customerServices.findCustomerByCustcode(custcode);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
    //    http://localhost:2019/customers/namelike/mes
    @GetMapping(value = "/namelike/{custname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByCustnameLike(@PathVariable String custname)
    {
        List<Customer> rtnlist = customerServices.findByNameLike(custname);
        return new ResponseEntity<>(rtnlist, HttpStatus.OK);
    }

    //  http://localhost:2019/customers/orders/count
    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?> getOrderCounts()
    {
        List<OrderCounts> rtnList = customerServices.getOrderCounts();
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    //  http://localhost:2019/customers/orders/advanceamount
    @GetMapping(value = "orders/advanceamount", produces = "application/json")
    public ResponseEntity<?> getAdvanceAmount()
    {
        List<AdvanceAmounts> rtnList = customerServices.getAdvanceAmounts();
        return new ResponseEntity<>(rtnList,HttpStatus.OK);
    }

//    POST http://localhost:2019/customers/customer
    @PostMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid
                                            @RequestBody
                                                Customer newCustomer)
    {
        newCustomer.setCustcode(0);
        newCustomer = customerServices.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{custcode}")
            .buildAndExpand(newCustomer.getCustcode())
            .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(newCustomer,responseHeaders, HttpStatus.CREATED);
    }
//    PUT http://localhost:2019/customers/customer/19
    @PutMapping(value = "/customer/{custcode}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCompleteCustomer(@Valid @RequestBody Customer updateCustomer,
                                                    @PathVariable long custcode)
    {
        updateCustomer.setCustcode(custcode);
        updateCustomer = customerServices.save(updateCustomer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

//    PATCH http://localhost:2019/customers/customer/19
    @PatchMapping(value = "customer/{custcode}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer updateCustomer,
                                            @PathVariable long custcode)
    {
        updateCustomer = customerServices.update(updateCustomer, custcode);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

//    DELETE http://localhost:2019/customers/customer/54
    @DeleteMapping(value = "customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode)
    {
        customerServices.delete(custcode);
        return new  ResponseEntity<>(HttpStatus.OK);
    }
}
