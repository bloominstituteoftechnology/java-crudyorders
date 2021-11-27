package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.repositories.CustomerRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerservices")
public class CustomerServicesImpl implements CustomerServices{
    @Autowired
    private CustomerRepository customerrepos;

    @Autowired
    private AgentServices agentServices;


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
    @Transactional
    @Override
    public Customer save(Customer customer) {

        //Put & Post
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0){
            customerrepos.findById(customer.getCustcode())
                    .orElseThrow(()-> new EntityNotFoundException("Customer" + customer.getCustcode() + "Not Found"));
            newCustomer.setCustcode(customer.getCustcode());
        }
        Agent agent =  agentServices.findAgentById(customer.getAgent().getAgentcode());
        newCustomer.setAgent(agent);

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

        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders()){
            Order newOrder = new Order();
            newOrder.setOrdnum(o.getOrdnum());
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrderdescription(o.getOrderdescription());

            newOrder.setCustomer(newCustomer);
            newCustomer.getOrders().add(newOrder);
        }
        return customerrepos.save(newCustomer);
    }
    @Transactional
    @Override
    public Customer update(long id, Customer customer) {
        Customer updateCustomer = customerrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));

        if (customer.getCustname()!= null ) {
            updateCustomer.setCustname(customer.getCustname());
        }
        if (customer.getCustcity() != null ) {
            updateCustomer.setCustcity(customer.getCustcity());
        }
        if (customer.getWorkingarea() != null ) {
            updateCustomer.setWorkingarea(customer.getWorkingarea());
        }
        if (customer.getCustcountry() != null ) {
            updateCustomer.setCustcountry(customer.getCustcountry());
        }
        if (customer.getGrade() != null ) {
            updateCustomer.setGrade(customer.getGrade());
        }
        if (customer.hasvalueforopeningamt){
            updateCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if (customer.hasvalueforpaymentamt){
            updateCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if (customer.hasvalueforeceiveamt){
            updateCustomer.setReceiveamt(customer.getReceiveamt());
        }
        if (customer.hasvalueforoutstandingamt){
            updateCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if(customer.getPhone()!= null){
            updateCustomer.setPhone(customer.getPhone());
        }

        if (customer.getOrders().size() > 0) {
            //OneToMany -> new resources that arent in the database yet
            updateCustomer.getOrders().clear();
            for (Order o : customer.getOrders()) {
                Order newOrder = new Order();
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setOrderdescription(o.getOrderdescription());

                newOrder.setCustomer(updateCustomer);

                updateCustomer.getOrders().add(newOrder);
            }
        }


        return customerrepos.save(updateCustomer);
    }

    @Transactional
    @Override
    public void delete(long id) {
        customerrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));
        customerrepos.deleteById(id);
    }

}

