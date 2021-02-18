package com.javaorders.javaorders.services;

import com.javaorders.javaorders.models.Customer;
import com.javaorders.javaorders.views.OrderCount;

import java.util.List;

public interface CustomerServices
{
    public Customer save(Customer customer);

    List<Customer> findAllCustomersOrders();

    Customer findCustomerById(long custcode);
    List <Customer> findCustomerByLikeName(String likename);

    List<OrderCount> getOrderCount();
}
