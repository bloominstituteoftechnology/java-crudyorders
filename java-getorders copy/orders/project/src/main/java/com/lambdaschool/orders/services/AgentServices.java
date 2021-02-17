package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;

public interface AgentServices {

  Agent findAgentById(long agentid);

  Agent save(Agent agent);
}
