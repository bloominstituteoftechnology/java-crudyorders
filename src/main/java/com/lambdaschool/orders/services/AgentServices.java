package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;

import java.util.List;

public interface AgentServices
{
    Agent save(Agent agent);

    Agent findAgentByAgentcode(Long agentcode);
}
