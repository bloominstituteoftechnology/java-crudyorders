package com.lambda.javaorders.services;

import com.lambda.javaorders.models.Customer;
import com.lambda.javaorders.models.Order;
import com.lambda.javaorders.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices
{
    @Autowired
    CustomerRepository custrepos;

    @Transactional
    @Override
    public Customer save(Customer customer) {
        return custrepos.save(customer);
    }

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> list = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findCustomerById(long customerid)
    {
        return custrepos.findById(customerid)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customerid + " Not Found"));
    }

    @Override
    public List<Customer> findByNameLike(String subname) {
        return custrepos.findByCustnameContainingIgnoringCase(subname);
    }

}
