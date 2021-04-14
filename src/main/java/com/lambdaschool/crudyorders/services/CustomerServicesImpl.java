package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerservices")
public class CustomerServicesImpl implements CustomerServices{
    @Autowired
    private CustomerRepository customerrepos;


    @Override
    public List<Customer> findByNameLike(String thename)
    {
        List<Customer> list = customerrepos.findByCustnameContainingIgnoringCase(thename);
        return list;
    }

    @Override
    public Customer findCustomerById(long id) throws EntityNotFoundException {
        return customerrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("customer" + id +"not found"));
    }
    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        customerrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }
}

