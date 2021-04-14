package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomerRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
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

    @Autowired
    private PaymentRepository paymentrepos;

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

        newCust.getOrders().clear();
        for (Order o: customer.getOrders())
        {
            Order newOrder = new Order();
            newOrder.setOrderdescription(o.getOrderdescription());
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
        }

        newCust.getPayments().clear();
        for (Payment p : customer.getPayments())
        {
            Payment newPayment = paymentrepos.findById(p.getPaymentid())
                .orElseThrow(()-> new EntityNotFoundException("Payment " + p.getPaymentid() + " NotFound"));

            newCust.getPayments().add(newPayment);
        }

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

    @Transactional
    @Override
    public Customer update(Customer customer, long id)
    {
        Customer currentCustomer = findCustomerByCustcode(id);

        if (customer.getCustcode() != 0)
        {
            findCustomerByCustcode(customer.getCustcode());
            currentCustomer.setCustcode(customer.getCustcode());
        }

        if (customer.getCustname()!= null)
        {
            currentCustomer.setCustname(customer.getCustname());
        }

        if (customer.getCustcity() != null)
        {
            currentCustomer.setCustcity(customer.getCustcity());
        }

        if (customer.getWorkingarea() != null)
        {
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.getCustcountry() != null)
        {
            currentCustomer.setCustcountry(customer.getCustcountry());
        }

        if (customer.getGrade()!= null)
        {
            currentCustomer.setGrade(customer.getGrade());
        }

        if (customer.hasvalueforopeningamt)
        {
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }

        if (customer.hasvalueforreceiveamt)
        {
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }

        if (customer.hasvalueforpaymentamt)
        {
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }

        if (customer.hasvalueforoutstandingamt)
        {
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }

        currentCustomer.setCustname(customer.getCustname());
        currentCustomer.setCustcity(customer.getCustcity());
        currentCustomer.setWorkingarea(customer.getWorkingarea());
        currentCustomer.setCustcountry(customer.getCustcountry());
        currentCustomer.setGrade(customer.getGrade());
        currentCustomer.setOpeningamt(customer.getOpeningamt());
        currentCustomer.setReceiveamt(customer.getReceiveamt());
        currentCustomer.setPaymentamt(customer.getPaymentamt());
        currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        currentCustomer.setPhone(customer.getPhone());

        if (customer.getOrders().size() > 0)
        {
            currentCustomer.getOrders().clear();
            for (Order o : customer.getOrders())
            {
                Order newOrder = new Order();
                newOrder.setOrderdescription(o.getOrderdescription());
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setAdvanceamount(o.getAdvanceamount());
            }
        }

        if (customer.getPayments().size() >0)
        {
            currentCustomer.getPayments().clear();
            for (Payment p : customer.getPayments())
            {
                Payment newPayment = paymentrepos.findById(p.getPaymentid())
                    .orElseThrow(()-> new EntityNotFoundException("Payment " + p.getPaymentid() + " NotFound"));

                currentCustomer.getPayments().add(newPayment);
            }
        }
        return customerrepos.save(currentCustomer);
    }
}
