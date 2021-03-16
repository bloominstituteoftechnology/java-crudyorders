package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerServices {

  List<Customer> findAllCustomers();

  Customer findCustomerById(long custcode);

  List<Customer> findByNameLike(String substring);


  /**
   * Deletes the customer record from the database based off of the provided primary key
   *
   * @param id id The primary key (long) of the customer you seek.
   */
  void delete(long id);

  /**
   * Updates the provided fields in the customer record referenced by the primary key.
   * <p>
   *
   * @param customer just the customer fields to be updated.
   * @param id         The primary key (long) of the customer to update
   * @return the complete customer object that got updated
   */
  Customer update(long id, Customer customer);

  /**
   * Given a complete customer object, saves that customer object in the database.
   * If a primary key is provided, the record is completely replaced
   * If no primary key is provided, one is automatically generated and the record is added to the database.
   *
   * @param customer the customer object to be saved
   * @return the saved customer object including any automatically generated fields
   */
  Customer save(Customer customer);

}
