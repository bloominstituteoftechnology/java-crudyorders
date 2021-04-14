package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.repositories.CustomerRepository;
import com.lambdaschool.orders.views.AdvanceAmounts;
import com.lambdaschool.orders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices
{
    @Autowired
    private CustomerRepository customerrepos;

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCust = new Customer();
        if(customer.getCustcode() != 0)
        {
            customerrepos.findById(customer.getCustcode())
                .orElseThrow(()-> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found"));

            newCust.setCustcode(customer.getCustcode());
        }

        newCust.setCustname(customer.getCustname());
        newCust.setCustcity(customer.getCustcity());
        newCust.setWorkingarea(customer.getWorkingarea());
        newCust.setCustcountry(customer.getCustcountry());
        newCust.setGrade(customer.getGrade());
        newCust.setOpeningamt(customer.getOpeningamt());
        newCust.setReceiveamt(customer.getReceiveamt());
        newCust.setPaymentamt(customer.getPaymentamt());
        newCust.setOutstandingamt(customer.getOutstandingamt());
        newCust.setPhone(customer.getPhone());

        return customerrepos.save(customer);
    }

    @Override
    public List<Customer> findAllCustomersOrders()
    {
        List<Customer> rtnlist = new ArrayList<>();
        customerrepos.findAll().iterator().forEachRemaining(rtnlist::add);
        return rtnlist;
    }

    @Override
    public Customer findCustomerByCustcode(long custcode)
    {
        return customerrepos.findById(custcode)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " Not Found"));
    }

    @Override
    public List<Customer> findByNameLike(String custname)
    {
        List<Customer> list = customerrepos.findByCustnameContainingIgnoringCase(custname);

        return list;
    }

    @Override
    public List<OrderCounts> getOrderCounts()
    {
        List<OrderCounts> rtnList = customerrepos.findOrderCounts();
        return rtnList;
    }

    @Override
    public List<AdvanceAmounts> getAdvanceAmounts()
    {
        List<AdvanceAmounts> rtnList = customerrepos.findAdvanceAmounts();
        return rtnList;
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if (customerrepos.findById(id).isPresent())
        {
            customerrepos.deleteById(id);
        }else
        {
            throw new EntityNotFoundException("Customer " + id + " Not Found");
        }
    }

    @Override
    public Customer update(
        Customer customer,
        long id)
    {
        return null;
    }
}
