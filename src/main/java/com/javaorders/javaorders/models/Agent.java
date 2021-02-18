package com.javaorders.javaorders.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//AGENTS
//
//    AGENTCODE primary key, not null Long
//    AGENTNAME string
//    WORKINGAREA string
//    COMMISSION double
//    PHONE string
//    COUNTRY string

@Entity
@Table(name = "agents")
public class Agent
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long agentcode;

    private String agentname;
    private String workingarea;

    private double commision;

    private String phone;
    private String country;

    @OneToMany(mappedBy = "agent",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<Customer> customers = new ArrayList<>();

    public Agent()
    {
    }


    public Agent(
        String agentname,
        String workingarea,
        double commision,
        String phone,
        String country)
    {
        this.agentname = agentname;
        this.workingarea = workingarea;
        this.commision = commision;
        this.phone = phone;
        this.country = country;
    }

    public long getAgentcode()
    {
        return agentcode;
    }

    public void setAgentcode(long agentcode)
    {
        this.agentcode = agentcode;
    }

    public String getAgentname()
    {
        return agentname;
    }

    public void setAgentname(String agentname)
    {
        this.agentname = agentname;
    }

    public String getWorkingarea()
    {
        return workingarea;
    }

    public void setWorkingarea(String workingarea)
    {
        this.workingarea = workingarea;
    }

    public double getCommision()
    {
        return commision;
    }

    public void setCommision(double commision)
    {
        this.commision = commision;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public List<Customer> getCustomers()
    {
        return customers;
    }

    public void setCustomers(List<Customer> customers)
    {
        this.customers = customers;
    }
}
