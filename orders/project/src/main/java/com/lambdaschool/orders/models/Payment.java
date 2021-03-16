package com.lambdaschool.orders.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="payments")
public class Payment {
  //(paymentid, type)

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long paymentid;

  private String type;

  @ManyToMany(mappedBy = "payments")
  Set<Order> orders = new HashSet<>();


  /* -------- CONSTRUCTORS -------- */

  public Payment() {
  }

  public Payment(String type) {
    this.type = type;
  }

  /* -------- GETTERS AND SETTERS -------- */

  public long getPaymentid() {
    return paymentid;
  }

  public void setPaymentid(long paymentid) {
    this.paymentid = paymentid;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}

