package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.OrdersRepository;
import com.lambdaschool.javaorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices{

    @Autowired
    private OrdersRepository orderrepos;

    @Autowired
    private CustomerServices customerServices;

    @Autowired
    private PaymentRepository paymentRepos;

    @Transactional
    @Override
    public Order save(Order order) {
        Order newOrder = new Order();

        if ( order.getOrdnum() != 0){
            orderrepos.findById(order.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found!"));

            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setCustomer(customerServices.findCustomerById(order.getCustomer().getCustcode()));
        newOrder.setOrderdescription(order.getOrderdescription());

        newOrder.getPayments().clear();
        for (Payment p : order.getPayments()){
            Payment newpay = paymentRepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));

            newOrder.getPayments().add(newpay);
        }

        return orderrepos.save(newOrder);
    }

    @Override
    public Order findOrderById(long id) {
        return orderrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found!"));
    }

    @Transactional
    @Override
    public Order update(Order order, long id) {

        Order currentOrder = orderrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found"));

        if (order.hasordamount){
            currentOrder.setOrdamount(order.getOrdamount());
        }
        if (order.hasadvanceamount){
            currentOrder.setAdvanceamount(order.getAdvanceamount());
        }
        if (order.getCustomer() != null){
            currentOrder.setCustomer(customerServices.findCustomerById(order.getCustomer().getCustcode()));
        }
        if (order.getOrderdescription() != null){
            currentOrder.setOrderdescription(order.getOrderdescription());
        }
        if (order.getPayments()
        .size() > 0){
            currentOrder.getPayments().clear();
            for (Payment p : order.getPayments()){
                Payment newPay = paymentRepos.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));

                currentOrder.getPayments().add(newPay);
            }
        }
        return orderrepos.save(currentOrder);
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (orderrepos.findById(id)
            .isPresent()){
                orderrepos.deleteById(id);
        }else {
            throw new EntityNotFoundException("Order " + id + " Not Found!");
        }
    }

}
