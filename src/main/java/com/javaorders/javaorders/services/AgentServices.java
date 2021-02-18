package com.javaorders.javaorders.services;

import com.javaorders.javaorders.models.Agent;

public interface AgentServices
{
    public Agent save(Agent agent);

    Agent findAgentById(long agentcode);
}
