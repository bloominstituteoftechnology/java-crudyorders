package com.lambdaschool.crudyorders.services;


import com.lambdaschool.crudyorders.models.*;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CustomerServicesImpl implements CustomerServices {

  @Autowired
  private AgentServices agentServices;

  @Autowired
  private PaymentServices paymentServices;

  @Autowired
  private CustomersRepository custrepos;

  @Override //JPA Query
  public List<Customer> findAllOrders() {
    List<Customer> list = new ArrayList<>();
    custrepos.findAll().iterator().forEachRemaining(list::add);
    return list;
  }

  @Override //JPA Query
  public List<Customer> findAllCustomers() {
    List<Customer> list = new ArrayList<>();
    custrepos.findAll().iterator().forEachRemaining(list::add);
    return list;
  }


  @Override //JPA Query
  public Customer findCustomerById(long custcode) {
    return custrepos.findById(custcode)
        .orElseThrow(()-> new
            EntityNotFoundException("Customer " + custcode + " not found!"));
  }

  @Override //JPA Query
  public Customer findCustomerByCustname(String custname) {
    Customer rtnCustomer = custrepos.findByCustname(custname);
    return null;
  }

  @Override //JPA Query
  public List<Customer> findByCustnameLike(String subcustname) {
    List<Customer> customerList = custrepos
        .findByCustnameContainingIgnoringCase(subcustname);
    return customerList;
  }

  @Override //Custom SQL Query
  public List<OrderCounts> getOrderCounts() {
    List<OrderCounts> rtnList = custrepos.findOrderCounts();
    return rtnList;
  }

  /**
   * New method bodies
   * @param id
   */
  @Transactional
  @Override
  public void delete(long id) {
    if (custrepos.findById(id).isPresent()){
      custrepos.deleteById(id);
    }
    else {
      throw new EntityNotFoundException("Customer id: " + id + " not found!");
    }
  }

  @Transactional
  @Override
  public Customer update(Customer customer, long id) {
    Customer currentCustomer = findCustomerById(id);

    if (customer.getCustname() != null) {
      currentCustomer.setCustname(customer.getCustname());
    }

    if (customer.getCustcity() != null) {
      currentCustomer.setCustcity(customer.getCustcity());
    }

    if (customer.getWorkingarea() != null) {
      currentCustomer.setWorkingarea(customer.getWorkingarea());
    }

    if (customer.getCustcountry() != null) {
      currentCustomer.setCustcountry(customer.getCustcountry());
    }

    if (customer.getGrade() != null) {
      currentCustomer.setGrade(customer.getGrade());
    }
    //handle all primitive field types
    if (customer.hasvalueforopeningamt) {
      currentCustomer.setOpeningamt(customer.getOpeningamt());
    }

    if (customer.hasvalueforreceiveamt) {
      currentCustomer.setReceiveamt(customer.getReceiveamt());
    }

    if (customer.hasvalueforpaymentamt) {
      currentCustomer.setPaymentamt(customer.getPaymentamt());
    }

    if (customer.hasvalueforoutstandingamt) {
      currentCustomer.setOutstandingamt(customer.getOutstandingamt());
    }

    if (customer.getOrders().size() > 0) {
      currentCustomer.getOrders().clear();
      for (Order o : customer.getOrders()) {
        Order newOrder = new Order();

        newOrder.setOrdamount(o.getOrdamount());
        newOrder.setPayments(o.getPayments());
        newOrder.setOrderdescription(o.getOrderdescription());
        //Update OneToMany Orders List
        currentCustomer.getOrders().add(newOrder);
      }
    }

      //ManyToOne
    if (customer.getAgent() != null) {
      customer.setAgent(agentServices.findAgentById(
          customer.getAgent().getAgentcode()));
    }
    return custrepos.save(currentCustomer);
  }

  @Transactional
  @Override
  public Customer save(Customer customer) {
    Customer newCustomer = new Customer();

    if (customer.getCustcode() != 0) {
      custrepos.findById(customer.getCustcode())
               .orElseThrow(()-> new EntityNotFoundException(
                   "Customer " + customer.getCustcode() + " not found!"));

      newCustomer.setCustcode(customer.getCustcode());
    }
    newCustomer.setCustname(customer.getCustname());
    newCustomer.setCustcity(customer.getCustcity());
    newCustomer.setWorkingarea(customer.getWorkingarea());
    newCustomer.setCustcountry(customer.getCustcountry());
    newCustomer.setGrade(customer.getGrade());
    newCustomer.setOpeningamt(customer.getOpeningamt());
    newCustomer.setReceiveamt(customer.getReceiveamt());
    newCustomer.setPaymentamt(customer.getPaymentamt());
    newCustomer.setOutstandingamt(customer.getOutstandingamt());
    newCustomer.setPhone(customer.getPhone());

    //ManyToOne = the one has to already assist
    //I renamed the Agent object to agent in customer class
    newCustomer.setAgent(agentServices.findAgentById(
        customer.getAgent().getAgentcode()));

    //OneToMany
    newCustomer.getOrders().clear();
    for(Order o : customer.getOrders()){
      Order newOrder = new Order();

      newOrder.setCustomer(newCustomer);
      newOrder.setOrdamount(o.getOrdamount());
      newOrder.setAdvanceamount(o.getAdvanceamount());
      newOrder.setOrderdescription(o.getOrderdescription());

      newOrder.getPayments().clear();
      for (Payment p : o.getPayments()){
        Payment newPayment = paymentServices.findPaymentById(p.getPaymentid());
        newOrder.getPayments().add(newPayment);
      }
      newCustomer.getOrders().add(newOrder);
    }
    return custrepos.save(newCustomer);
  }

  @Override
  public void deleteAllCustomers() {
    custrepos.deleteAll();
  }

}
