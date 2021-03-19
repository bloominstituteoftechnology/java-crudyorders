package com.lambdaschool.javaorders.services;


import com.lambdaschool.javaorders.models.Agent;
import com.lambdaschool.javaorders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "agentServices")
public class AgentServicesImpl implements AgentServices {
    @Autowired
    AgentsRepository agentrepos;

    @Transactional
    @Override
    public Agent save(Agent agent)
    {
        return agentrepos.save(agent);
    }

    @Override
    public Agent findAgentById(long agentid) {
        return agentrepos.findById(agentid)
        .orElseThrow(() -> new EntityNotFoundException("Agent #" + agentid + " not found!"));
    }
}
