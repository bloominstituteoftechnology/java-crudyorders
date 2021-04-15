package com.lambdaschool.crudyorders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties(value = {"hasvalueforordamount",
    "hasvalueforadvanceamount"})
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long ordnum;

  @Transient
  public boolean hasvalueforordamount = false;
  private double ordamount;

  @Transient
  public boolean hasvalueforadvanceamount = false;
  private double advanceamount;

  @ManyToMany()
  @JoinTable(name="orderspayments",
      joinColumns = @JoinColumn(name="ordnum"),
      inverseJoinColumns = @JoinColumn(name="paymentid"))
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
   Set<Payment> payments = new HashSet<>();

  private String orderdescription;

  @ManyToOne
  @JoinColumn(name ="custcode", nullable = false)
  @JsonIgnoreProperties(value = "orders", allowSetters = true)
  private Customer customer;

  public Order() {
  }

  public Order(
      double ordamount,
      double advanceamount,
      Customer customer,
      String orderdescription)
  {
    this.ordamount = ordamount;
    this.advanceamount = advanceamount;
    this.orderdescription = orderdescription;
    this.customer = customer;
  }

  public long getOrdnum() {
    return ordnum;
  }

  public void setOrdnum(long ordnum) {
    this.ordnum = ordnum;
  }

  public double getOrdamount() {
    return ordamount;
  }

  public void setOrdamount(double ordamount) {
    hasvalueforordamount = true;
    this.ordamount = ordamount;
  }

  public double getAdvanceamount() {
    return advanceamount;
  }

  public void setAdvanceamount(double advanceamount) {
    hasvalueforadvanceamount = true;
    this.advanceamount = advanceamount;
  }

  public Set<Payment> getPayments() {
    return payments;
  }

  public String getOrderdescription() {
    return orderdescription;
  }

  public void setOrderdescription(String orderdescription) {
    this.orderdescription = orderdescription;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setPayments(Set<Payment> payments) {
    this.payments = payments;
  }

}
