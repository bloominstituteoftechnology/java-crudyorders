package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customers;

import java.util.List;

public interface CustomersService
{

    List<Customers> findAllCustomers();

    Customers findByCustomerId(long id);

    List<Customers> findCustomersByLikeName(String name);

    Customers save(Customers customers); //POST

    void delete(long id); //DELETE

    Customers update(Customers customers, long id); //PATCH or PUT

    void deleteAllCustomers();

}
