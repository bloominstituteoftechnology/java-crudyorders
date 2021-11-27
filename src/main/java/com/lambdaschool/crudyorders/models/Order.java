package com.lambdaschool.crudyorders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties(value = {"hasvalueforordamout","hasvalueforadvanceamount"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ordnum;

    private double ordamount;
    private double advanceamount;
    private String orderdescription;
    @Transient
    public boolean hasvalueforordamout = false;
    @Transient
    public boolean hasvalueforadvanceamount =false;



    @ManyToMany()
    @JoinTable(name = "orderspayments",
            joinColumns = @JoinColumn(name="ordnum"),
            inverseJoinColumns = @JoinColumn(name = "paymentid"))
    @JsonIgnoreProperties(value = "orders",allowSetters = true)
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "custcode", nullable = false)
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Customer customer;


    public Order() {
    }

    public Order(double ordamount, double advanceamount, String orderdescription, Customer customer) {
        this.ordamount = ordamount;
        this.advanceamount = advanceamount;
        this.orderdescription = orderdescription;
        this.customer = customer;
    }

    public double getOrdamount() {
        return ordamount;
    }

    public void setOrdamount(double orderamt) {
        hasvalueforordamout=true;
        this.ordamount = orderamt;
    }

    public double getAdvanceamount() {
        return advanceamount;
    }

    public void setAdvanceamount(double advanceamt) {
        hasvalueforadvanceamount =true;
        this.advanceamount = advanceamt;
    }

    public String getOrderdescription() {
        return orderdescription;
    }

    public void setOrderdescription(String orderdescription) {
        this.orderdescription = orderdescription;
    }

    public long getOrdnum() {
        return ordnum;
    }

    public void setOrdnum(long ordernum) {
        this.ordnum = ordernum;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
