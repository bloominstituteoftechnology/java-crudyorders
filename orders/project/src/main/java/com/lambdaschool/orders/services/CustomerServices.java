package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerServices {
  public Customer save(Customer customer);

  List<Customer> findAllCustomers();

  Customer findCustomerById(long custcode);

  List<Customer> findByNameLike(String substring);
}
