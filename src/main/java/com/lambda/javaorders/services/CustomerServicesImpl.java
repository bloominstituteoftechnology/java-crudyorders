package com.lambda.javaorders.services;

import com.lambda.javaorders.models.Customer;
import com.lambda.javaorders.models.Order;
import com.lambda.javaorders.repositories.CustomerRepository;
import com.lambda.javaorders.repositories.OrderRepository;
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

    @Autowired
    OrderRepository orderrepos;

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0)
        {
            custrepos.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found"));
            newCustomer.setCustcode(customer.getCustcode());
        }

        // primitive data type / String
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setAgent(customer.getAgent());



        return custrepos.save(newCustomer);
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

    @Transactional
    @Override
    public void delete(long custid) {
        if (custrepos.findById(custid).isPresent())
        {
            custrepos.deleteById(custid);
        } else
        {
            throw new EntityNotFoundException("Customer " + custid + " Not Found");
        }
    }

    @Transactional
    @Override
    public Customer update(Customer updateCustomer, long custid) 
    {
        Customer currentCustomer = custrepos.findById(custid)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + custid + " Not Found"));

        // primitive data type / String
        if (currentCustomer.getCustcity() != null)
        {
            currentCustomer.setCustcity(updateCustomer.getCustcity());
        }

        if (currentCustomer.getCustname() != null)
        {
            currentCustomer.setCustname(updateCustomer.getCustname());
        }

        if (currentCustomer.getCustcountry() != null)
        {
            currentCustomer.setCustcountry(updateCustomer.getCustcountry());
        }

        if (currentCustomer.getGrade() != null)
        {
            currentCustomer.setGrade(updateCustomer.getGrade());
        }

        if (currentCustomer.hasvalueforpaymentamt)
        {
            currentCustomer.setPaymentamt(updateCustomer.getPaymentamt());
        }

        if (currentCustomer.hasvalueforreceiveamt)
        {
            currentCustomer.setReceiveamt(updateCustomer.getReceiveamt());
        }

        if (currentCustomer.hasvalueforopeningamt)
        {
            currentCustomer.setOpeningamt(updateCustomer.getOpeningamt());
        }

        if (currentCustomer.hasvalueforoutstandingamt)
        {
            currentCustomer.setOutstandingamt(updateCustomer.getOutstandingamt());
        }

        if (currentCustomer.getPhone() != null)
        {
            currentCustomer.setPhone(updateCustomer.getPhone());
        }

        if (currentCustomer.getWorkingarea() != null)
        {
            currentCustomer.setWorkingarea(updateCustomer.getWorkingarea());
        }

        if (currentCustomer.getAgent() != null)
        {
            currentCustomer.setAgent(updateCustomer.getAgent());
        }

        // collections are a complete replace
        // collections many to many
        if (updateCustomer.getOrders().size() > 0)
        {
            currentCustomer.getOrders().clear();
            for (Order o : updateCustomer.getOrders())
            {
                Order newOrder = orderrepos.findById(o.getOrdnum())
                        .orElseThrow(() -> new EntityNotFoundException("Order " + o.getOrdnum() + " Not Found"));
                currentCustomer.getOrders().add(newOrder);
            }
        }

        return custrepos.save(currentCustomer);
    }

}
