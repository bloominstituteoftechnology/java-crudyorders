package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;

import java.util.List;

public interface CustomerServices
{
    Customer save(Customer customer);

    List<Customer> findAllOrders();
    Customer findCustomerById(long id);
    List<Customer> findByNameLike(String subname);
    Customer update( Customer customer, long id);

    void delete(long id);
}
