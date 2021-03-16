package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomerRepository;
import com.lambdaschool.orders.repositories.OrderRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices{
  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public Order findOrderById(Long ordernum) throws EntityNotFoundException {
    return orderRepository.findById(ordernum)
        .orElseThrow(() -> new EntityNotFoundException("Order " + ordernum + " not found."));
  }

  @Override
  public List<Order> findOrderWithAdvanceAmount() {
    return orderRepository.findOrderByAdvanceamountGreaterThan(0.0);
  }

  @Transactional
  @Override
  public Order save(Order receivedOrder) {

    Order newOrder = new Order();

    //put or post
    //if we get order number, check if valid
    //if valid, set order number to number received in request body
    if (receivedOrder.getOrdnum() != 0) {
      //put
      orderRepository.findById(receivedOrder.getOrdnum())
          .orElseThrow(() -> new EntityNotFoundException("Order " + receivedOrder.getOrdnum() + " not found."));
      newOrder.setOrdnum(receivedOrder.getOrdnum());
    }

    newOrder.getPayments().clear();
    for (Payment p : receivedOrder.getPayments()) {
      Payment newPayment = paymentRepository.findById(p.getPaymentid())
          .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));

      newOrder.getPayments().add(newPayment);
    }

    //set up newOrder object by pulling info from the request body
    newOrder.setOrdamount(receivedOrder.getOrdamount());
    newOrder.setOrderdescription(receivedOrder.getOrderdescription());
    newOrder.setAdvanceamount(receivedOrder.getAdvanceamount());

    newOrder.setCustomer(
        customerRepository.findById(
            receivedOrder.getCustomer().getCustcode()
        )
      .orElseThrow(
          () -> new EntityNotFoundException(
              "Restaurant " + receivedOrder.getCustomer().getCustcode() + " not found."
          )
      )
    );

    //save the newOrder object, replacing existing
    return orderRepository.save(newOrder);
  }


  @Transactional
  @Override
  public Order update(long id, Order receivedOrder) {
    Order newOrder = orderRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));

    if (receivedOrder.hasvalueforordamount) {
      newOrder.setOrdamount(receivedOrder.getOrdamount());
    }
    if (receivedOrder.getOrderdescription() != null) {
      newOrder.setOrderdescription(receivedOrder.getOrderdescription());
    }
    if (receivedOrder.hasvalueforadvanceamount) {
      newOrder.setAdvanceamount(receivedOrder.getAdvanceamount());
    }
    if (receivedOrder.getCustomer() != null) {
      newOrder.setCustomer(receivedOrder.getCustomer());
    }

    if (receivedOrder.getPayments().size() > 0) {
      newOrder.getPayments().clear();
      for (Payment p : receivedOrder.getPayments()) {
        Payment newPayment = paymentRepository.findById(p.getPaymentid())
            .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));

        newOrder.getPayments().add(newPayment);
      }
    }
    return orderRepository.save(newOrder);
  }

  @Transactional
  @Override
  public void delete(long id) {
    if (orderRepository.findById(id).isPresent()) {
      orderRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException("Order " + id + " not found!");
    }
  }
}