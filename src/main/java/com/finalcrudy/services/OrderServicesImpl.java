package com.finalcrudy.services;

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
@Service(value = "orderService")
public class OrderServicesImpl
    implements OrderServices
{
    @Autowired
    private OrderRepository orderrepos;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Order> findOrdersWithAdvanceAmount(double advanceamount)
    {
        List<Order> list = orderrepos.findAllByAdvanceamountGreaterThan(0.00);
        return list;
    }

    @Override
    public Order findOrdersByNum(long ordnum) throws
                                         EntityNotFoundException
    {
        return orderrepos.findByNum(num)
            .orElseThrow(() -> new EntityNotFoundException("Order " + num + " Not Found"));
    }

    @Transactional
    @Override
    public Order save(Order order)
    {
        Order newOrder = new Order();

        if (order.getOrdnum() != 0) {
            orderrepos.findByOrdnum(order.getOrdnum())
                .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found!"));
            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        newOrder.getPayments().clear();
        for (Payment p : order.getPayments()) {
            Payment newPayment = paymentRepository.findById(p.getPaymentid())
                .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));
            newOrder.getPayments().add(newPayment);
        }

        return orderrepos.save(newOrder);
    }

    @Transactional
    @Override
    public void delete(long ordnum) {
        orderrepos.deleteByOrdnum(ordnum);
    }

    @Transactional
    @Override
    public Order update(Order order, long ordnum) {
        Order updateOrder = orderrepos.findByOrdnum(ordnum)
            .orElseThrow(() -> new EntityNotFoundException("Order " + ordnum + " Not Found!"));

        if (order.getOrdamount() != 0.00) updateOrder.setOrdamount(order.getOrdamount());
        if (order.getAdvanceamount() != 0.00) updateOrder.setAdvanceamount(order.getAdvanceamount());
        if (order.getOrderdescription() != null) updateOrder.setOrderdescription(order.getOrderdescription());

        if (order.getPayments().size() > 0) {
            updateOrder.getPayments().clear();
            for (Payment p : order.getPayments()) {
                Payment newPayment = paymentRepository.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));
                updateOrder.getPayments().add(newPayment);
            }
        }


        return orderrepos.save(updateOrder);
    }

    @Transactional
    @Override
    public void deleteAll() {
        orderrepos.deleteAll();
    }
}
