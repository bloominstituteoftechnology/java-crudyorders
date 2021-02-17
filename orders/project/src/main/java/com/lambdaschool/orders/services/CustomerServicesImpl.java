package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices {
  @Autowired
  private CustomerRepository customerRepository;

  @Transactional
  @Override
  public Customer save(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public List<Customer> findAllCustomers() {
    List<Customer> list = new ArrayList<>();
    customerRepository.findAll().iterator().forEachRemaining(list::add);
    return list;
  }

  @Override
  public Customer findCustomerById(long custcode) {
    return customerRepository.findById(custcode)
        .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " not found."));
  }

  @Override
  public List<Customer> findByNameLike(String substring) {
    List<Customer> list = customerRepository.findCustomerByCustnameContainingIgnoringCase(substring);
    return list;
  }
}
