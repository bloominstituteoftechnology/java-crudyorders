package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;

import java.util.List;

public interface AgentServices {
  Agent save(Agent agent);

  List<Agent> findAllAgents();

  Agent findAgentById(long id);
}
