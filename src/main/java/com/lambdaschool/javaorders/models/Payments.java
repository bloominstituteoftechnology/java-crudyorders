package com.lambdaschool.javaorders.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payments")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    @Column(nullable = false)
    private long paymentid;

    @Column(nullable = false)
    private String type;

    public Payments() {
    }

    public Payments(String type) {
        this.type = type;
    }

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

    @ManyToMany()
    @JoinTable(name = "orderspayments",
        joinColumns = @JoinColumn(name = "ordernum"),
        inverseJoinColumns = @JoinColumn(name = "paymentid"))
    Set<Payments> payments = new HashSet<>();
}
