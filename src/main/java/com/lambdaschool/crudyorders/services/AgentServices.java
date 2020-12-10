package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;

public interface AgentServices
{
    Agent findByAgentCode(long id);
}
