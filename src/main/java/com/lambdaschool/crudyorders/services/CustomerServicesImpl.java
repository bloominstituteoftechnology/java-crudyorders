package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.repositories.AgentsRepository;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.views.OrderCounts;
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
    private CustomersRepository custrepos;

    @Autowired
    private AgentsRepository agentrepos;

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> list = new ArrayList<>();

        custrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findByCustomerCode(long id)
    {
        return custrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found!"));
    }

    @Override
    public List<Customer> findAllCustomersByNameLike(String custname)
    {
        return custrepos.findByCustnameContainingIgnoringCase(custname);
    }

    @Override
    public List<OrderCounts> getOrderCounts()
    {
        List<OrderCounts> list = custrepos.findOrderCounts();
        return list;
    }

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer(); // create new blank obj

        if (customer.getCustcode() != 0)
        {
            custrepos.findById(customer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found!"));

            newCustomer.setCustcode(customer.getCustcode());
        }

        // runs through setter so any validation we have in setter happens
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
        newCustomer.setAgent(customer.getAgent());

        // one to many - orders
        newCustomer.getOrders().clear();
        for(Order o : customer.getOrders())
        {
            Order newOrder = new Order();
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrderdescription(o.getOrderdescription());
            newOrder.setCustomer(newCustomer); // associated order item w customer
            newCustomer.getOrders().add(newOrder); // associated customer w order item
        }

        // many to one agent
        Agent newAgent = agentrepos.findById(newCustomer.getAgent().getAgentcode())
            .orElseThrow(()-> new EntityNotFoundException("Agent " + newCustomer.getAgent().getAgentcode() + " Not Found"));
        newCustomer.setAgent(newAgent);

        return custrepos.save(newCustomer);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if (custrepos.findById(id).isPresent()) // did it find id in our list
        {
            custrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException("Restaurant " + id + " Not Found");
        }

    }

}
