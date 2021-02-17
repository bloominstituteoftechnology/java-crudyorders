package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "agentServices")
public class AgentServicesImpl implements AgentServices {
  @Autowired
  private AgentRepository agentRepository;

  @Transactional
  @Override
  public Agent save(Agent agent) {
    return agentRepository.save(agent);
  }

//  @Override
//  public Customer findCustomerById(long custcode) {
//    return customerRepository.findById(custcode)
//        .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " not found."));
//  }

  @Override
  public Agent findAgentById(long agentid) {
    Agent agent = agentRepository.findById(agentid)
        .orElseThrow(() -> new EntityNotFoundException("Agent " + agentid + " not found."));
    return agent;
  }
}
