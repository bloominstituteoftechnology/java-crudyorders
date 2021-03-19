package com.lambdaschool.javaorders.services;


import com.lambdaschool.javaorders.models.Customers;
import com.lambdaschool.javaorders.models.Orders;
import com.lambdaschool.javaorders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerservice")
public class CustomersServiceImpl implements CustomersService {

    @Autowired
    CustomersRepository customerrepo;

    @Override
    public List<Customers> findAllCustomers() {
        List<Customers> list = new ArrayList<>();
        customerrepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customers findByCustomerId(long id)
    {
        Customers rtnCust = customerrepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer" + id + " Not Found"));
        return rtnCust;
    }

    @Override
    public List<Customers> findCustomersByLikeName(String name)
    {
        List<Customers> rtnList = customerrepo.findByNameContainingIgnoreCase(name);
        return rtnList;
    }


    @Transactional
    @Override
    public void delete(long id) {
        if (customerrepo.findById(id).isPresent()) {
            customerrepo.deleteById(id);
        } else {
            throw new EntityNotFoundException("Customer " + id + " Not Found");
        }
    }

    @Transactional
    @Override
    public Customers update(Customers customers, long id) {
        Customers updateCustomer = customerrepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));


        if (customers.getCustname() != null) {
            updateCustomer.setCustname(customers.getCustname());
        }
        if (customers.getCustcity() != null) {
            updateCustomer.setCustcity(customers.getCustcity());
        }
        if (customers.getCustcountry() != null) {
            updateCustomer.setCustcountry(customers.getCustcountry());
        }
        if (customers.getGrade() != null) {
            updateCustomer.setGrade(customers.getGrade());
        }
        if (customers.hasValueForOpeningamt == true) {
            updateCustomer.setOpeningamt(customers.getOpeningamt());
        }
        if (customers.hasValueForOutstandingamt == true) {
            updateCustomer.setOutstandingamt(customers.getOutstandingamt());
        }
        if (customers.hasValueForPaymentamt == true) {
            updateCustomer.setPaymentamt(customers.getPaymentamt());
        }
        if (customers.getPhone() != null) {
            updateCustomer.setPhone(customers.getPhone());
        }
        if (customers.hasValueForReceiveamt == true) {
            updateCustomer.setReceiveamt(customers.getReceiveamt());
        }
        if (customers.getWorkingarea() != null) {
            updateCustomer.setWorkingarea(customers.getWorkingarea());
        }


        //agents manyToOne


        //orders oneToMany
        if (customers.getOrders().size() > 0) {
            updateCustomer.getOrders().clear();
            for (Orders o : customers.getOrders()) {
                Orders newOrder = new Orders();
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setOrderdescription(o.getOrderdescription());
                newOrder.setCustomers(updateCustomer);

                updateCustomer.getOrders().add(newOrder);
            }
        }

            return customerrepo.save(updateCustomer);
    }

    @Transactional
    @Override
    public Customers save(Customers customers)
    {
        Customers newCustomer = new Customers();

        if (customers.getCustcode() != 0){
            customerrepo.findById(customers.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + customers.getCustcode() + " Not Found"));
            newCustomer.setCustcode(customers.getCustcode());
        }
        newCustomer.setCustname(customers.getCustname());
        newCustomer.setCustcity(customers.getCustcity());
        newCustomer.setCustcountry(customers.getCustcountry());
        newCustomer.setGrade(customers.getGrade());
        newCustomer.setOpeningamt(customers.getOpeningamt());
        newCustomer.setOutstandingamt(customers.getOutstandingamt());
        newCustomer.setPaymentamt(customers.getPaymentamt());
        newCustomer.setPhone(customers.getPhone());
        newCustomer.setReceiveamt(customers.getReceiveamt());
        newCustomer.setWorkingarea(customers.getWorkingarea());


        //agents manyToOne


        //orders oneToMany
        newCustomer.getOrders().clear();
        for (Orders o : customers.getOrders()) {
            Orders newOrder = new Orders();
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setOrderdescription(o.getOrderdescription());
            newOrder.setCustomers(newCustomer);

            newCustomer.getOrders().add(newOrder);
        }

        return customerrepo.save(newCustomer);
    }

    @Transactional
    @Override
    public void deleteAllCustomers() {
        customerrepo.deleteAll();
    }
}
