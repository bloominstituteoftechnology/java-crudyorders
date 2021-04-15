package com.lambdaschool.crudyorders.controller;//package com.lambdaschool.modelorders.controller;
//
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.CustomerServices;
import com.lambdaschool.crudyorders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

//
@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  private OrderServices orderServices;

  //http://localhost:2019/orders/order
  @GetMapping(value = "/order",
      produces = "application/json")
  public ResponseEntity<?> listAllOrders(){
    List<Order> rtnList = orderServices.findAllOrders();
    return new ResponseEntity<>(rtnList, HttpStatus.OK);
  }


//  http://localhost:2019/orders/order/7
  @GetMapping(value = "/order/{orderid}",
      produces = "application/json")
  public ResponseEntity<?> findOrderById(
      @PathVariable long orderid) {
    Order rtnOrder = orderServices.findOrderById(orderid);
    return new ResponseEntity<>(rtnOrder,
                                HttpStatus.OK);
  }

  //  POST http://localhost:2019/orders/order
  @PostMapping(value = "/order", consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder){
    newOrder.setOrdnum(0);
    newOrder = orderServices.save(newOrder);

    HttpHeaders responseHeaders = new HttpHeaders();
    URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("/{custcode}")
                                                    .buildAndExpand(newOrder.getOrdnum())
                                                    .toUri();
    responseHeaders.setLocation(newCustomerURI);
    return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
  }
  //  PUT http://localhost:2019/orders/order/63
  @PutMapping(value = "/order/{orderid}", consumes = "application/json")
  public ResponseEntity<?> updateFullOrder(@Valid @RequestBody Order updateOrder,
       @PathVariable long orderid) {
    updateOrder.setOrdnum(orderid);
    orderServices.save(updateOrder);
    return new ResponseEntity<>(updateOrder, HttpStatus.OK);
  }

  /**
   * Delete the Restaurant record associated with the given id. The associated menu items, and Restaurant Payments items are also deleted. Payments is unaffected.
   * <br> Example: <a href="http://localhost:2019/restaurants/restaurant/4">http://localhost:2019/restaurants/restaurant/4</a>
   *
   * @param orderid The primary key of the restaurant you wish to delete.
   * @return No body is returned. A status of OK is returned if the deletion is successful.
   * @see OrderServices#delete(long) OrderServices.delete(long)
   */
  //  DELETE http://localhost:2019/orders/order/58
  @DeleteMapping(value = "/order/{orderid}")
  public ResponseEntity<?> deleteOrderById(
      @PathVariable long orderid){
    orderServices.delete(orderid);
    return new ResponseEntity<>(HttpStatus.OK);
  }


}

