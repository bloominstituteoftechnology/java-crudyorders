package com.lambda.javaorders.services;

import com.lambda.javaorders.models.Agent;
import com.lambda.javaorders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "agentServices")
public class AgentServicesImpl implements AgentServices
{
    @Autowired
    AgentRepository agentrepos;

    @Transactional
    @Override
    public Agent save(Agent agent) {
        return agentrepos.save(agent);
    }

    @Override
    public Agent findAgentById(long agentid) {
        return agentrepos.findById(agentid)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + agentid + " Not Found"));
    }
}
