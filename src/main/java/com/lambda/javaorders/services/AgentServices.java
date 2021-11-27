package com.lambda.javaorders.services;

import com.lambda.javaorders.models.Agent;

public interface AgentServices
{
    Agent save(Agent agent);

    Agent findAgentById(long agentid);
}
