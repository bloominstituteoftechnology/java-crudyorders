package com.javaorders.javaorders.repositories;

import com.javaorders.javaorders.models.Customer;
import com.javaorders.javaorders.views.OrderCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long>
{
    List<Customer> findCustomerByCustnameContainingIgnoringCase(String likename);

    @Query(value = "SELECT c.custname, count(o.ordnum) AS orders " +
        "FROM customers c LEFT JOIN orders o " +
        "ON c.custcode = o.custcode " +
        "GROUP BY c.custname", nativeQuery = true)
    List<OrderCount> getOrderCount();
}
