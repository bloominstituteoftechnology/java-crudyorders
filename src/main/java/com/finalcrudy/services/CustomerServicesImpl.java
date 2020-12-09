package com.finalcrudy.services;

import com.finalcrudy.models.Customer;
import com.finalcrudy.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Service(value = "customersService")
public class CustomerServiceImpl
    implements CustomerServices
{

    @Autowired
    private CustomerRepository custrepos;

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> list = new ArrayList<>();

        custrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Customer> findByCustomerName(String custname)
    {
        return custrepos.findByCustnameContainingIgnoringCase(custname);
    }

    @Override
    public Customer findCustomersById(long id) throws
                                               EntityNotFoundException
    {
        return custrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
    }
}