package com.lambdaschool.crudyorders.controller;//package com.lambdaschool.modelorders.controller;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.services.CustomerServices;
import com.lambdaschool.crudyorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

//  http://localhost:2019/customers/orders
  @GetMapping(value = "/orders",
      produces = "application/json")
  public ResponseEntity<?> listAllOrders(){
    List<Customer> rtnList = customerServices.findAllOrders();
    return new ResponseEntity<>(rtnList, HttpStatus.OK);
  }

//http://localhost:2019/customers/customer
  @GetMapping(value = "/customer", produces = "application/json")
  public ResponseEntity<?> listAllCustomers(){
    List<Customer> customerList = customerServices.findAllCustomers();
    return new ResponseEntity<>(customerList, HttpStatus.OK);
  }

//  http://localhost:2019/customers/customer/7
//  http://localhost:2019/customers/customer/77
  @GetMapping(value = "/customer/{custid}",
      produces = "application/json")
  public ResponseEntity<?> findCustomerById(
      @PathVariable long custid){
    Customer rtnCust = customerServices.findCustomerById(custid);
    return new ResponseEntity<>(rtnCust, HttpStatus.OK);
}

//  http://localhost:2019/customers/namelike/mes
//  http://localhost:2019/customers/namelike/cin
  @GetMapping(value = "namelike/{subcustname}",
      produces = "application/json")
  public ResponseEntity<?> findCustomerByCustname(@PathVariable String subcustname){
    List<Customer> rtnList = customerServices.findByCustnameLike(subcustname);
    return new ResponseEntity<>(rtnList, HttpStatus.OK);
  }

  //http://localhost:2019/customers/orders/count
  @GetMapping(value = "/orders/count", produces = "application/json")
  public ResponseEntity<?> getOrderCounts(){
    List<OrderCounts> rtnList = customerServices.getOrderCounts();
    return new ResponseEntity<>(rtnList, HttpStatus.OK);
  }

  /**
   * Delete the Restaurant record associated with the given id. The associated menu items, and Restaurant Payments items are also deleted. Payments is unaffected.
   * <br> Example: <a href="http://localhost:2019/restaurants/restaurant/4">http://localhost:2019/restaurants/restaurant/4</a>
   *
   * @param custid The primary key of the restaurant you wish to delete.
   * @return No body is returned. A status of OK is returned if the deletion is successful.
   * @see CustomerServices#delete(long) RestaurantServices.delete(long)
   */
  //  DELETE http://localhost:2019/customers/customer/54
  @DeleteMapping(value ="/customer/{custid}")
  public ResponseEntity<?> deleteCustomerById(
      @PathVariable long custid){
    customerServices.delete(custid);
    return new ResponseEntity<>(HttpStatus.OK);
  }

//  POST http://localhost:2019/customers/customer
    @PostMapping(value = "/customer", consumes = "application/json",
        produces = "application/json")
    public ResponseEntity<?> addNewCustomer(
        @Valid @RequestBody Customer newCustomer) {

    newCustomer.setCustcode(0);
    newCustomer = customerServices.save(newCustomer);

      HttpHeaders responseHeaders = new HttpHeaders();
      URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{custcode}")
          .buildAndExpand(newCustomer.getCustcode())
          .toUri();
      responseHeaders.setLocation(newCustomerURI);

      return new ResponseEntity<>(newCustomer, responseHeaders,
                                  HttpStatus.CREATED);
    }

//  PUT http://localhost:2019/customers/customer/19
    @PutMapping(value = "/customer/{custid}",
          consumes = "application/json")
  public ResponseEntity<?> updateFullCustomer(
      @Valid @RequestBody Customer updateCustomer,
      @PathVariable long custid) {
    updateCustomer.setCustcode(custid);
    customerServices.save(updateCustomer);
    return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

//  PATCH http://localhost:2019/customers/customer/19
    @PatchMapping(value = "/customer/{custid}",
         consumes = "application/json")
    public ResponseEntity<?> updateCustomer(
        @RequestBody Customer updateCustomer,
        @PathVariable long custid) {
    customerServices.update(updateCustomer, custid);
    return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
  }
}

