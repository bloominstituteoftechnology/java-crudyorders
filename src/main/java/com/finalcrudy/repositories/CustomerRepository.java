package com.finalcrudy.repositories;

import com.finalcrudy.models.Customer;
import com.finalcrudy.repositories.CustomerRepository;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository
    extends CrudRepository<Customer, Long>
{
}