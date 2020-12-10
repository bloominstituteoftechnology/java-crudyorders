package com.finalcrudy.services;

import com.finalcrudy.models.Customer;
import com.finalcrudy.repositories.CustomerRepository;
import com.finalcrudy.models.Order;
import com.finalcrudy.repositories.OrderRepository;
import com.finalcrudy.models.Payment;
import com.finalcrudy.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Service(value = "customersService")
public class CustomerServiceImpl
    implements CustomerServices
{

    @Autowired
    private CustomerRepository custrepos;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> list = new ArrayList<>();

        custrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Customer> findByCustName(String custname)
    {
        return custrepos.findByCustnameContainingIgnoringCase(custname);
    }

    @Override
    public Customer findCustomersByCode(long custcode) throws
                                               EntityNotFoundException
    {
        return custrepos.findByCustcode(custcode)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " Not Found"));
    }

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0) {
            custrepos.findByCustcode(customer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found!"));
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

// not entirely sure if I need this payments section
//        newCustomer.getType().clear();
//        for (Payment p : customer.getType()) {
//            Payment newPayment = paymentRepository.findById(p.getPaymentid())
//                .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));
//            newCustomer.getType().add(newPayment);
//        }

        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders()) {
            Order newOrder = new Order();
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setOrderdescription(o.getOrderdescription());
            newOrder.setCustomer(newCustomer);

            newCustomer.getOrders().add(newOrder);
        }

        return custrepos.save(newCustomer);
    }

    @Transactional
    @Override
    public void delete(long custcode) {
        custrepos.deleteByCustcode(custcode);
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long custcode) {
        Customer updateCustomer = custrepos.findByCustcode(custcode)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " Not Found!"));
//

        if (customer.getCustname() != null) updateCustomer.setCustname(customer.getCustname());
        if (customer.getCustcity() != null) updateCustomer.setCustcity(customer.getCustcity());
        if (customer.getWorkingarea() != null) updateCustomer.setWorkingarea(customer.getWorkingarea());
        if (customer.getCustcountry() != null) updateCustomer.setCustcountry(customer.getCustcountry());
        if (customer.getGrade() != null) updateCustomer.setGrade(customer.getGrade());
        if (customer.getOpeningamt() != 0.00) updateCustomer.setOpeningamt(customer.getOpeningamt());
        if (customer.getReceiveamt() != 0.00) updateCustomer.setReceiveamt(customer.getReceiveamt());
        if (customer.getPaymentamt() != 0.00) updateCustomer.setPaymentamt(customer.getPaymentamt());
        if (customer.getOutstandingamt() != 0.00) updateCustomer.setOutstandingamt(customer.getOutstandingamt());
        if (customer.getPhone() != null) updateCustomer.setPhone(customer.getPhone());


//        if (order.getPayments().size() > 0) {
//            updateOrder.getPayments().clear();
//            for (Payment p : order.getPayments()) {
//                Payment newPayment = paymentRepository.findById(p.getPaymentid())
//                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));
//                updateOrder.getPayments().add(newPayment);
//            }
//        }

        if (customer.getOrders().size() > 0) {
            updateCustomer.getOrders().clear();
            for (Order o : customer.getOrders()) {
                Order newOrder = new Order();
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setOrderdescription(o.getOrderdescription());
                newOrder.setCustomer(newCustomer);

                newCustomer.getOrders().add(newOrder);
            }
        }

        return custrepos.save(updateCustomer);
    }

    @Transactional
    @Override
    public void deleteAll() {
        custrepos.deleteAll();
    }
}