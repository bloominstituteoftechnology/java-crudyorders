package com.lambdaschool.javaorders.services;


import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.repositories.AgentsRepository;
import com.lambdaschool.javaorders.repositories.CustomersRepository;
import com.lambdaschool.javaorders.repositories.OrdersRepository;
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
    CustomersRepository custrepos;

    @Autowired
    OrdersRepository orderrepos;

    @Autowired
    AgentsRepository agentrepos;

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();
        if (customer.getCustcode() != 0)
        {
            custrepos.findById(customer.getCustcode())
            .orElseThrow
                    (() -> new EntityNotFoundException
                    ("Customer " + customer.getCustcode() + " not found!"));
            newCustomer.setCustcode(customer.getCustcode());
        }
        // Primitives Validation
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setAgent(agentrepos.findById(customer.getAgent()
                .getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent()
                        .getAgentcode() + " Not Found")));

        newCustomer.getOrders().clear();
        for(Order o: customer.getOrders())
        {
            Order newOrder = new Order();
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrderdescription(o.getOrderdescription());
            newOrder.setCustomer(newCustomer);

            newCustomer.getOrders().add(newOrder);
        }
        return custrepos.save(newCustomer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> list = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findCustomerById(long custcode) {
        return custrepos.findById(custcode)
        .orElseThrow(() -> new EntityNotFoundException("Customer #" + custcode + " not found!" ));
    }

    @Override
    public List<Customer> findByNameLike(String subname) {
        return custrepos.findByCustnameContainingIgnoringCase(subname);
    }

    @Transactional
    @Override
    public Customer update(Customer updateCustomer, long custcode) {
        Customer currentCustomer = custrepos.findById(custcode)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " not found!"));
        if (updateCustomer.getCustcode() != 0)
        {
            custrepos.findById(updateCustomer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " not found!"));
        }

        if (updateCustomer.getCustname() != null)
        {
            currentCustomer.setCustname(updateCustomer.getCustname());
        }

        if (updateCustomer.getCustcity() != null)
        {
            currentCustomer.setCustcity(updateCustomer.getCustcity());
        }

        if (updateCustomer.getWorkingarea() != null)
        {
            currentCustomer.setWorkingarea(updateCustomer.getWorkingarea());
        }

        if (updateCustomer.getCustcountry() != null)
        {
            currentCustomer.setCustcountry(updateCustomer.getCustcountry());
        }

        if (updateCustomer.getGrade() != null)
        {
            currentCustomer.setGrade(updateCustomer.getGrade());
        }

        if (updateCustomer.getPhone() != null)
        {
            currentCustomer.setPhone(updateCustomer.getPhone());
        }

        if (updateCustomer.hasvalueforopeningamt)
        {
            currentCustomer.setOpeningamt(updateCustomer.getOpeningamt());
        }

        if (updateCustomer.hasvalueforrecieveamt)
        {
            currentCustomer.setReceiveamt(updateCustomer.getReceiveamt());
        }

        if (updateCustomer.hasvalueforpaymentamt)
        {
            currentCustomer.setPaymentamt(updateCustomer.getPaymentamt());
        }

        if (updateCustomer.hasvalueforoutstandingamt)
        {
            currentCustomer.setOutstandingamt(updateCustomer.getOutstandingamt());
        }

        //payments:agents :: menus : orders

        return custrepos.save(currentCustomer);

    }

    @Transactional
    @Override
    public void delete(long custcode) {
        {
            if (custrepos.findById(custcode).isPresent())
            {
                custrepos.deleteById(custcode);
            } else
            {
              throw new EntityNotFoundException("You can't delete " + custcode + " ,it doesn't exist!");
            }
        }
    }


}
