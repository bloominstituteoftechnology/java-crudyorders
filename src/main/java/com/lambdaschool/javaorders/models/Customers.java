package com.lambdaschool.javaorders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long custcode;

    @Column(nullable = false)
    private String custname;

    private String custcity;
    private String workingarea;
    private String custcountry;
    private String grade;

    @JsonIgnoreProperties(value = "hasValueForOpeningamt", allowSetters = true)
    @Transient
    public boolean hasValueForOpeningamt = false;
    private double openingamt;

    @JsonIgnoreProperties(value = "hasValueForReceiveamt", allowSetters = true)
    @Transient
    public boolean hasValueForReceiveamt = false;
    private double receiveamt;

    @JsonIgnoreProperties(value = "hasValueForPaymentamt", allowSetters = true)
    @Transient
    public boolean hasValueForPaymentamt = false;
    private double paymentamt;

    @JsonIgnoreProperties(value = "hasValueForOutstandingamt", allowSetters = true)
    @Transient
    public boolean hasValueForOutstandingamt = false;
    private double outstandingamt;


    private String phone;

    //many customers to one agent
    @ManyToOne
    @JoinColumn(name = "agentcode", nullable = false)
    Agents agents;

    @OneToMany(mappedBy = "custcode",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "custcode", allowSetters = true)
    List<Orders> orders = new ArrayList<>();

    public Customers() {
    }

    public Customers(String custname, String custcity, String workingarea, String custcountry, String grade, double openingamt, double receiveamt, double paymentamt, double outstandingamt, String phone) {
        this.custname = custname;
        this.custcity = custcity;
        this.workingarea = workingarea;
        this.custcountry = custcountry;
        this.grade = grade;
        this.openingamt = openingamt;
        this.receiveamt = receiveamt;
        this.paymentamt = paymentamt;
        this.outstandingamt = outstandingamt;
        this.phone = phone;
    }

    public long getCustcode() {
        return custcode;
    }

    public void setCustcode(long custcode) {
        this.custcode = custcode;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustcity() {
        return custcity;
    }

    public void setCustcity(String custcity) {
        this.custcity = custcity;
    }

    public String getWorkingarea() {
        return workingarea;
    }

    public void setWorkingarea(String workingarea) {
        this.workingarea = workingarea;
    }

    public String getCustcountry() {
        return custcountry;
    }

    public void setCustcountry(String custcountry) {
        this.custcountry = custcountry;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getOpeningamt() {
        return openingamt;
    }

    public void setOpeningamt(double openingamt) {
        hasValueForOpeningamt = true;
        this.openingamt = openingamt;
    }

    public double getReceiveamt() {
        return receiveamt;
    }

    public void setReceiveamt(double receiveamt) {
        hasValueForReceiveamt = true;
        this.receiveamt = receiveamt;
    }

    public double getPaymentamt() {
        return paymentamt;
    }

    public void setPaymentamt(double paymentamt) {
        hasValueForPaymentamt = true;
        this.paymentamt = paymentamt;
    }

    public double getOutstandingamt() {
        return outstandingamt;
    }

    public void setOutstandingamt(double outstandingamt) {
        hasValueForOutstandingamt = true;
        this.outstandingamt = outstandingamt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Agents getAgents() {
        return agents;
    }

    public void setAgents(Agents agents) {
        this.agents = agents;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
