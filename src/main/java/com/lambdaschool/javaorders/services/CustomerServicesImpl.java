package com.lambdaschool.javaorders.services;
import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices
{
    @Autowired
    CustomersRepository custrepos;

    @Autowired
    private AgentServices agentServices;

    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0){
            custrepos.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer" + customer.getCustcode() + " Not Found"));

            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setWorkingarea(customer.getWorkingarea());

        newCustomer.setAgent(agentServices.findAgentById(customer.getAgent().getAgentcode()));

        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders()){
            Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), newCustomer, o.getOrderdescription());

            newCustomer.getOrders()
                    .add(newOrder);
        }

        return custrepos.save(newCustomer);



    }

    @Override
    public List<Customer> findAllOrders() {
        List<Customer> list = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findCustomerById(long id) {
        return custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found!"));
    }

    @Override
    public List<Customer> findByNameLike(String subname) {
        List<Customer> rtnList = custrepos.findByCustnameContainingIgnoringCase(subname);
        return rtnList;
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long id) {
        Customer currentCustomer = custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found!"));

       if (customer.getCustname() != null){
           currentCustomer.setCustname(customer.getCustname());
       }
       if (customer.getCustcity() != null){
           currentCustomer.setCustcity(customer.getCustcity());

       }
       if (customer.getCustcountry() != null){
           currentCustomer.setCustcountry(customer.getCustcountry());

       }
       if (customer.getGrade() != null){
           currentCustomer.setGrade(customer.getGrade());

       }
       if (customer.hasopeningamt){
           currentCustomer.setOpeningamt(customer.getOpeningamt());

       }
       if (customer.hasoutstandingamt){
           currentCustomer.setOutstandingamt(customer.getOutstandingamt());

       }
       if (customer.haspaymentamt){
           currentCustomer.setPaymentamt(customer.getPaymentamt());

       }
       if (customer.getPhone() != null){
           currentCustomer.setPhone(customer.getPhone());

       }
       if (customer.hasreceiveamt){
           currentCustomer.setReceiveamt(customer.getReceiveamt());

       }
       if (customer.getWorkingarea() != null){
           currentCustomer.setWorkingarea(customer.getWorkingarea());

       }
       if (customer.getAgent() != null) {
           currentCustomer.setAgent(agentServices.findAgentById(customer.getAgent().getAgentcode()));
       }

       if (customer.getOrders()
                .size() > 0){
           currentCustomer.getOrders().clear();
           for (Order o : customer.getOrders()){
               Order newOrder =  new Order(o.getOrdamount(), o.getAdvanceamount(), currentCustomer, o.getOrderdescription());

               currentCustomer.getOrders()
                       .add(newOrder);
           }
       }

       return custrepos.save(currentCustomer);
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (custrepos.findById(id)
                .isPresent()){
            custrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Customer " + id + " Not Found!");
        }

    }
}
