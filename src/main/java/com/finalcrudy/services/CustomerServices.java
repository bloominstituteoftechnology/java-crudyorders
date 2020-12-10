package com.finalcrudy.services;

import com.finalcrudy.models.Customer;

import java.util.List;


public interface CustomerServices
{

    List<Customer> findAllCustomers();


    List<Customer> findByCustomerName(String custname);

    List<Customer> findCustomerByCode(long custcode);


    Customer findCustomersById(long id);

    Customer save(Customer customer); //POST

    void delete(long custcode); //DELETE

    Customer update(Customer customer, long custcode); //PATCH vs PUT

    void deleteAll();
}