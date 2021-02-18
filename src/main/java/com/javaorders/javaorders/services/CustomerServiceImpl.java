package com.javaorders.javaorders.services;


import com.javaorders.javaorders.models.Customer;
import com.javaorders.javaorders.models.Order;
import com.javaorders.javaorders.repositories.CustomersRepository;
import com.javaorders.javaorders.views.OrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServiceImpl implements CustomerServices
{
    @Autowired
    private CustomersRepository customersrepos;

    @Override
    public List<Customer> findAllCustomersOrders() {
        List<Customer> customerOrderList = new ArrayList<>();
        customersrepos.findAll().iterator().forEachRemaining(customerOrderList::add);


        return customerOrderList;
    }

    @Override
    public Customer findCustomerById(long custcode) {
        Customer customer = customersrepos.findById(custcode).orElseThrow(() -> new EntityNotFoundException());
        return customer;
    }

    @Override
    public List<Customer> findCustomerByLikeName(String likename) {
        List<Customer> customerList = customersrepos.findCustomerByCustnameContainingIgnoringCase(likename);
        return customerList;
    }

    @Override
    public List<OrderCount> getOrderCount() {
        List<OrderCount> orderCount = customersrepos.getOrderCount();
        return orderCount;
    }

    @Transactional
    @Override
    public Customer save(Customer tempCustomer)
    {
        Customer newCustomer = new Customer();

        //PUT or a POST
        if (tempCustomer.getCustcode() != 0) {
            //PUT
            customersrepos.findById(tempCustomer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + tempCustomer.getCustcode() + " not found!"));
            newCustomer.setCustcode(tempCustomer.getCustcode());
        }

        newCustomer.setCustname(tempCustomer.getCustname());
        newCustomer.setCustcity(tempCustomer.getCustcity());
        newCustomer.setWorkingarea(tempCustomer.getWorkingarea());
        newCustomer.setCustcountry(tempCustomer.getCustcountry());
        newCustomer.setGrade(tempCustomer.getGrade());
        newCustomer.setOpeningamt(tempCustomer.getOpeningamt());
        newCustomer.setRecieveamt(tempCustomer.getRecieveamt());
        newCustomer.setPaymentamt(tempCustomer.getPaymentamt());
        newCustomer.setOpeningamt(tempCustomer.getOutstandinggamt());
        newCustomer.setPhone(tempCustomer.getPhone());
        newCustomer.setAgent(tempCustomer.getAgent());

        newCustomer.getOrders().clear();
        for (Order m : tempCustomer.getOrders()) {
            Order newMenu = new Order();
            newMenu.setDish(m.getDish());
            newMenu.setPrice(m.getPrice());
            newMenu.setRestaurant(newCustomer);

            newCustomer.getMenus().add(newMenu);
        }


        return customersrepos.save(newCustomer);
    }
}
