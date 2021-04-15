package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;

import java.util.List;

public interface CustomerServices {

    List<Customer> findByNameLike(String thename);

    Customer findCustomerById(long id);

    List<Customer> findAllCustomers();

    Customer save(Customer newCustomer);

    Customer update(long id , Customer customer);

    void delete(long id);
}
