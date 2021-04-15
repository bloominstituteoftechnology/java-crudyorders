package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.OrderRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    private OrderRepository orderrepos;
    @Autowired
    private CustomerServices customerServices;

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    @Override
    public Order save(Order order)
    {
        Order newOrder = new Order();
        if(order.getOrdnum()!= 0)
        {
            findOrderByOrdnum(order.getOrdnum());
            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.setCustomer(customerServices.findCustomerByCustcode(order.getCustomer().getCustcode()));

        newOrder.getPayments().clear();
        for (Payment p : order.getPayments())
        {
            Payment newPayment = paymentRepository.findById(p.getPaymentid())
                .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
        }

        return orderrepos.save(newOrder);
    }

    @Override
    public Order findOrderByOrdnum(long ordnum)
    {
        return orderrepos.findById(ordnum)
            .orElseThrow(() -> new EntityNotFoundException("Order " + ordnum + " Not Found"));
    }

    @Override
    public Order update(
        Order order,
        long id)
    {
        return null;
    }

    @Transactional
    @Override
    public void delete(long ordnum)
    {
        if (orderrepos.findById(ordnum).isPresent())
        {
            orderrepos.deleteById(ordnum);
        }else
        {
            throw new EntityNotFoundException("Order " + ordnum + " Not Found");
        }
    }

}
