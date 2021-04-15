package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.OrderRepository;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Service(value="orderservice")
public class OrderServicesImpl implements OrderServices {

    @Autowired
    private OrderRepository orderrepos;

    @Autowired
    private PaymentRepository paymentrepos;

    @Autowired
    private CustomerServices customerServices;

    @Override
    public List<Order> findAllOrders() {

        List<Order> list = new ArrayList<>();
        orderrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Order findOrderById(long id) throws EntityNotFoundException {
        return orderrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order" + id + "not found"));

    }

    @Transactional
    @Override
    public Order save(Order order) {
        Order newOrder = new Order();

        //POST -> new resource
        //PUT -> replace existing resource
        if (order.getOrdnum() != 0) {
            orderrepos.findById(order.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found!"));
            newOrder.setOrdnum(order.getOrdnum());
        }
        Customer newCustomer = customerServices.findCustomerById(order.getCustomer().getCustcode());
        newOrder.setCustomer(newCustomer);
        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());


        //ManyToMany -> existing database entities
        newOrder.getPayments().clear();
        for (Payment p : order.getPayments()) {
            Payment newPayment = paymentrepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));

            newOrder.getPayments().add(newPayment);
        }

        return orderrepos.save(newOrder);
    }


    @Transactional
    @Override
    public void delete(long id) {
        orderrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));
        orderrepos.deleteById(id);

    }

    @Transactional
    @Override
    public Order update(long id, Order order) {
        Order updateOrder = orderrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));

        if (order.getOrderdescription() != null) {
            updateOrder.setOrderdescription(order.getOrderdescription());
        }
        if (order.hasvalueforordamout) {
            updateOrder.setOrdamount(order.getOrdamount());
        }
        if (order.hasvalueforadvanceamount) {
            updateOrder.setAdvanceamount(order.getAdvanceamount());
        }


        return orderrepos.save(updateOrder);
    }

}


