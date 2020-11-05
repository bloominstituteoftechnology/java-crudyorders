package com.finalcrudy.services;

import com.finalcrudy.models.Customer;

import java.util.List;


public interface CustomerServices
{

    List<Customer> findAllCustomers();


    List<Customer> findByCustomerName(String custname);


    Customer findCustomersById(long id);
}