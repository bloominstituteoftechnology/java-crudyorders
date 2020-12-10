package com.lambdaschool.crudyorders.repositories;

import com.lambdaschool.crudyorders.models.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Order, Long>
{
    @Query(value = "SELECT o.ordnum, c.custname \n" +
        "FROM orders o LEFT JOIN customers c \n" +
        "ON o.custcode = c.custcode \n" +
        "GROUP BY o.ordnum\n" +
        "ORDER BY c.custname", nativeQuery = true)
    Order findByOrderNum(long id);
}
