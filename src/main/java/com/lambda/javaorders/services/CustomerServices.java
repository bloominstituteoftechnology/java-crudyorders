package com.lambda.javaorders.services;

import com.lambda.javaorders.models.Customer;
import com.lambda.javaorders.models.Order;

import java.util.List;

public interface CustomerServices
{
    Customer save(Customer customer);

    List<Customer> findAllCustomers();

    Customer findCustomerById(long customerid);

    List<Customer> findByNameLike(String subname);

    void delete(long custid);

    Customer update(Customer updateCustomer, long custid);
}
