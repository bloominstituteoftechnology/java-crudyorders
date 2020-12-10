package com.finalcrudy.services;

import com.finalcrudy.models.Customer;

import java.util.List;


public interface CustomerServices
{

    List<Customer> findAllCustomers();

    List<Customer> findByCustname(String custname);

    List<Customer> findCustomerByCode(long custcode);

    Customer save(Customer customer); //POST

    void delete(long custcode); //DELETE

    Customer update(Customer customer, long custcode); //PATCH vs PUT

    void deleteAll();
}