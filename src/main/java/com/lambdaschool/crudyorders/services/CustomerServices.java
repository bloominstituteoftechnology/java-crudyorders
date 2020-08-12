package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.views.OrderCounts;

import java.util.List;

public interface CustomerServices
{
    List<Customer> findAllCustomers();

    Customer findByCustomerCode(long id);

    List<Customer> findAllCustomersByNameLike(String custname);

    List<OrderCounts> getOrderCounts();

    Customer save(Customer customer);

    void delete(long id);
}
