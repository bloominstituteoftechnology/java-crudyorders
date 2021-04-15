package com.lambdaschool.crudyorders.services;


import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.views.OrderCounts;

import java.util.List;


public interface CustomerServices {

  Customer save(Customer customer);

  List<Customer> findAllOrders();

  List<Customer> findAllCustomers();

  Customer findCustomerById(long custcode);

//  List<Customer> findAllCustomers();

  Customer findCustomerByCustname(String custname);

  List<Customer> findByCustnameLike(String subcustname);

  List<OrderCounts> getOrderCounts();

  void delete(long id);

  Customer update(Customer customer, long id);

  void deleteAllCustomers();
}
