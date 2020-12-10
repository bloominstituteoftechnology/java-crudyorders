package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    OrdersRepository ordrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Override
    public Order findByOrderNum(long id)
    {
        return ordrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found!"));
    }

    @Transactional
    @Override
    public Order save(Order order)
    {
        Order newOrder = new Order();

        if (order.getOrdnum() != 0)
        {
            ordrepos.findById(order.getOrdnum())
                .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found!"));

            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setCustomer(order.getCustomer());

        // many to many - payment must already exist
        newOrder.getPayments().clear(); // get payment set and clear it out
        // take payments sent into me and run 1 by 1, if valid, add to DB, if not, stop process and say invalid payment
        for (Payment p : order.getPayments())
        {
            Payment newPay = paymentrepos.findById(p.getPaymentid()) // find payment that matches in DB
                .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));

            newOrder.getPayments().add(newPay); // add to list of payments (associate to order we're adding)
        }

        return ordrepos.save(newOrder); // if id is 0, save (add to system) - if a num not 0, it will do a put (whole replace)
    }

    @Transactional
    @Override
    public Order update(Order order, long id)
    {
        Order currentOrder = ordrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found!"));

        if (order.getOrderdescription() != null) // if I got sent one, set current rest name to be that name
        {
            currentOrder.setOrderdescription(order.getOrderdescription());
        }

        // make changes in order model then..
        //fixme Seat Capacity
        if (order.hasvalueforadvanceamount) // if it's true, I change it, if it's false I don't
        {
            currentOrder.setAdvanceamount(order.getAdvanceamount());
        }

        if (order.hasvalueforordamount) // if it's true, I change it, if it's false I don't
        {
            currentOrder.setOrdamount(order.getOrdamount());
        }

        // many to many - payment must already exist
        if (order.getPayments().size() > 0)
        {
            currentOrder.getPayments().clear();
            for (Payment p : order.getPayments())
            {
                Payment newPay = paymentrepos.findById(p.getPaymentid()) // find payment that matches in DB
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));

                currentOrder.getPayments()
                    .add(newPay); // add to list of payments (associate to restaurant we're adding)
            }
        }
        return ordrepos.save(currentOrder);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if (ordrepos.findById(id).isPresent())
        {
            ordrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException("Order " + id + " Not Found!");
        }
//        ordrepos.findById(order.getOrdnum())
//            .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found!"));
//
//        ordrepos.deleteById(id);
    }
}
