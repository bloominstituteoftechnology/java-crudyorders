package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServicesImpl implements OrderServices{

  @Autowired
  private OrdersRepository orderrepos;

  @Autowired
  private CustomerServices customerServices;

  @Override
  public List<Order> findAllOrders() {
    List<Order> list = new ArrayList<>();
    orderrepos.findAll().iterator().forEachRemaining(list::add);
    return list;
  }

  @Override //JPA Query
  public Order findOrderById(long id) {
    return orderrepos.findById(id)
        .orElseThrow(()-> new
            EntityNotFoundException("Order " + id + " not found!"));
  }

  @Override
  public Order save(Order order) {
    Order newOrder = new Order();
    if (order.getOrdnum() != 0) {
      orderrepos.findById(order.getOrdnum())
                .orElseThrow(()-> new EntityNotFoundException(
                    "Order " + order.getOrdnum() + " not found!"));
      newOrder.setOrdnum(order.getOrdnum());
    }
    newOrder.setOrdamount(order.getOrdamount());
    newOrder.setAdvanceamount(order.getAdvanceamount());
    newOrder.setOrderdescription(order.getOrderdescription());

    //ManyToOne
    newOrder.setCustomer(customerServices.findCustomerById(
        order.getCustomer().getCustcode()));
    return orderrepos.save(newOrder);
  }

  @Override
  public Order update(Order order, long id) {
    Order currentOrder = findOrderById(id);

    if (order.getOrderdescription() != null) {
      currentOrder.setOrderdescription(order.getOrderdescription());
    }
    if (order.hasvalueforordamount){
      currentOrder.setOrdamount(order.getOrdamount());
    }
    if (order.hasvalueforadvanceamount) {
      currentOrder.setAdvanceamount(order.getAdvanceamount());
    }
    //ManyToOne
    if (order.getCustomer() != null) {
      order.setCustomer(customerServices.findCustomerById(
          order.getCustomer().getCustcode()));
    }
    return orderrepos.save(currentOrder);
  }

  @Override
  public void delete(long id) {
    if (orderrepos.findById(id).isPresent()){
      orderrepos.deleteById(id);
    }
    else {
      throw new EntityNotFoundException("Order " + id + " not found!");
    }
  }
}
