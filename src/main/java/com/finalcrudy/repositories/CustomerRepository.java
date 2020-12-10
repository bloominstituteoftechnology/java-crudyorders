package com.finalcrudy.repositories;

import com.finalcrudy.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
    Customer findByCustcode(long custcode);

    List<Customer> findByCustnameContainingIgnoringCase(String custname);


}