package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
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


  @Transactional
  @Override
  public Customer save(Customer receivedCustomer) {

    Customer newCustomer = new Customer();

    //put or post
    if (receivedCustomer.getCustcode() != 0) {
      customerRepository.findById(receivedCustomer.getCustcode())
          .orElseThrow(() -> new EntityNotFoundException("Customer " + receivedCustomer.getCustcode() + " not found."));
      newCustomer.setCustcode(receivedCustomer.getCustcode());
    }


    newCustomer.setCustname(receivedCustomer.getCustname());
    newCustomer.setCustcity(receivedCustomer.getCustcity());
    newCustomer.setWorkingarea(receivedCustomer.getWorkingarea());
    newCustomer.setCustcountry(receivedCustomer.getCustcountry());
    newCustomer.setGrade(receivedCustomer.getGrade());
    newCustomer.setOpeningamt(receivedCustomer.getOpeningamt());
    newCustomer.setReceiveamt(receivedCustomer.getReceiveamt());
    newCustomer.setPaymentamt(receivedCustomer.getPaymentamt());
    newCustomer.setOutstandingamt(receivedCustomer.getOutstandingamt());
    newCustomer.setPhone(receivedCustomer.getPhone());
    newCustomer.setAgent(receivedCustomer.getAgent());

    return customerRepository.save(newCustomer);
  }


  @Transactional
  @Override
  public Customer update(long id, Customer tempCustomer) {
    Customer updateCustomer = customerRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));

    if (tempCustomer.getCustname() != null) {
      updateCustomer.setCustname(tempCustomer.getCustname());
    }
    if (tempCustomer.getCustcity() != null) {
      updateCustomer.setCustcity(tempCustomer.getCustcity());
    }
    if (tempCustomer.getWorkingarea() != null) {
      updateCustomer.setWorkingarea(tempCustomer.getWorkingarea());
    }
    if (tempCustomer.getCustcountry() != null) {
      updateCustomer.setCustcountry(tempCustomer.getCustcountry());
    }
    if (tempCustomer.getGrade() != null) {
      updateCustomer.setGrade(tempCustomer.getGrade());
    }
    if (tempCustomer.hasvalueforopeningamt) {
      updateCustomer.setOpeningamt(tempCustomer.getOpeningamt());
    }
    if (tempCustomer.hasvalueforreceiveamt) {
      updateCustomer.setReceiveamt(tempCustomer.getReceiveamt());
    }
    if (tempCustomer.hasvalueforpaymentamt) {
      updateCustomer.setPaymentamt(tempCustomer.getPaymentamt());
    }
    if (tempCustomer.hasvalueforoutstandingamt) {
      updateCustomer.setOutstandingamt(tempCustomer.getOutstandingamt());
    }
    if (tempCustomer.getPhone() != null) {
      updateCustomer.setPhone(tempCustomer.getPhone());
    }
    if (tempCustomer.getAgent() != null) {
      updateCustomer.setAgent(tempCustomer.getAgent());
    }

    if (tempCustomer.getOrders().size() > 0) {
      updateCustomer.getOrders().clear();
      for (Order o : tempCustomer.getOrders()) {
        Order newOrder = new Order();
        newOrder.setOrdamount(o.getOrdamount());
        newOrder.setAdvanceamount(o.getAdvanceamount());
        newOrder.setOrderdescription(o.getOrderdescription());
        newOrder.setCustomer(updateCustomer);

        updateCustomer.getOrders().add(newOrder);
      }
    }
    return customerRepository.save(updateCustomer);
  }


    @Transactional
    @Override
    public void delete(long id) {
      if (customerRepository.findById(id).isPresent()) {
        customerRepository.deleteById(id);
      } else {
        throw new EntityNotFoundException("Customer " + " not found.");
      }
    }


}
