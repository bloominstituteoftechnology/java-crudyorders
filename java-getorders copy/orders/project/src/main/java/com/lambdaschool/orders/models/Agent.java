package com.lambdaschool.orders.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agents")
public class Agent {

  //agentcode, agentname, workingarea, commission, phone, country

  /* primary key */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long agentcode;

  /* name of agent */
  private String agentname;

  /* location of agent */
  private String workingarea;

  /* agent's commission */
  private double commission;

  /* agent's phone number */
  private String phone;

  /* agent's country */
  private String country;

  /* Link to the customers */
  @OneToMany(mappedBy = "agent")
  private List<Customer> customers = new ArrayList<>();

  /* -------- DEFAULT CONSTRUCTOR -------- */
  public Agent() {

  }

  /* -------- SECOND CONSTRUCTOR -------- */

  public Agent(String agentname, String workingarea, double commission, String phone, String country) {
    this.agentname = agentname;
    this.workingarea = workingarea;
    this.commission = commission;
    this.phone = phone;
    this.country = country;
  }

  /* -------- GETTERS AND SETTERS -------- */

  public long getAgentcode() {
    return agentcode;
  }

  public void setAgentcode(long agentcode) {
    this.agentcode = agentcode;
  }

  public String getAgentname() {
    return agentname;
  }

  public void setAgentname(String agentname) {
    this.agentname = agentname;
  }

  public String getWorkingarea() {
    return workingarea;
  }

  public void setWorkingarea(String workingarea) {
    this.workingarea = workingarea;
  }

  public double getCommission() {
    return commission;
  }

  public void setCommission(double commission) {
    this.commission = commission;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
