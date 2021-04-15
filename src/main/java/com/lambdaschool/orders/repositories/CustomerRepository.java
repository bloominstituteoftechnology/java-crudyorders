package com.lambdaschool.orders.repositories;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.views.AdvanceAmounts;
import com.lambdaschool.orders.views.OrderCounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
    Customer findByCustname(String custname);
    List<Customer> findByCustnameContainingIgnoringCase(String custname);

    @Query(value = "SELECT c.custname , count(ordnum) countorders " +
        "FROM customers c LEFT JOIN orders o " +
        "ON c.custcode = o.custcode " +
        "GROUP BY c.custname " +
        "ORDER BY countorders DESC",
         nativeQuery = true)
    List<OrderCounts> findOrderCounts();

    @Query(value = "SELECT c.custname , o.advanceamount " +
        "FROM customers c LEFT JOIN orders o " +
        "ON c.custcode =  o.custcode " +
        "WHERE o.advanceamount > 0 OR o.advanceamount != null " +
        "GROUP BY o.advanceamount, c.custname " +
        "ORDER BY o.advanceamount DESC",
        nativeQuery = true)
    List<AdvanceAmounts> findAdvanceAmounts();
}
