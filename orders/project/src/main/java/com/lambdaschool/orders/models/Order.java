package com.lambdaschool.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="orders")
@JsonIgnoreProperties({"hasvalueforadvanceamount", "hasvalueforordamount"})
public class Order {
  //ORDERS (ordnum, ordamount, advanceamount, custcode, orderdescription)

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long ordnum;

  private double ordamount;
  @Transient
  public boolean hasvalueforordamount = false;

  private double advanceamount;
  @Transient
  public boolean hasvalueforadvanceamount = false;

  @ManyToOne
  @JoinColumn(name = "custcode", nullable = false)
  @JsonIgnoreProperties(value = "orders",
      allowSetters = true)
  private Customer customer;

  private String orderdescription;

  @ManyToMany()
  @JoinTable(name="orderspayments",
      joinColumns = @JoinColumn(name="ordnum"),
      inverseJoinColumns = @JoinColumn(name="paymentid")
  )
  private Set<Payment> payments = new HashSet<>();

  public Order() {
  }

  public Order(double ordamount, double advanceamount, Customer customer, String orderdescription) {
    this.ordamount = ordamount;
    this.advanceamount = advanceamount;
    this.customer = customer;
    this.orderdescription = orderdescription;
  }

  public long getOrdnum() {
    return ordnum;
  }

  public void setOrdnum(long ordnum) {
    this.ordnum = ordnum;
  }

  public double getOrdamount() {
    hasvalueforordamount = true;
    return ordamount;
  }

  public void setOrdamount(double ordamount) {
    this.ordamount = ordamount;
  }

  public double getAdvanceamount() {
    return advanceamount;
  }

  public void setAdvanceamount(double advanceamount) {
    hasvalueforadvanceamount = true;
    this.advanceamount = advanceamount;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public String getOrderdescription() {
    return orderdescription;
  }

  public void setOrderdescription(String orderdescription) {
    this.orderdescription = orderdescription;
  }

  public Set<Payment> getPayments() {
    return payments;
  }

  public void setPayments(Set<Payment> payments) {
    this.payments = payments;
  }
}
