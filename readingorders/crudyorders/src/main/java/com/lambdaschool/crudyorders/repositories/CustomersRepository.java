package com.lambdaschool.crudyorders.repositories;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.views.OrderCounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long> {
  Customer findByCustname(String custname);
  List<Customer> findByCustnameContainingIgnoringCase(String likecustname);

  @Query(value = "SELECT c.custname name, " +
      "count(ordnum) countorders " +
      "FROM customers c LEFT JOIN orders o " +
      "ON c.custcode = o.custcode " +
      "GROUP BY c.custname " +
      "ORDER BY countorders DESC",
      nativeQuery = true)
  List<OrderCounts> findOrderCounts();
}
