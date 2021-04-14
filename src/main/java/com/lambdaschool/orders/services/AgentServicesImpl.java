package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service(value = "agentServices")
public class AgentServicesImpl implements AgentServices
{
    @Autowired
    private AgentRepository agentrepos;
    @Transactional
    @Override
    public Agent save(Agent agent)
    {
        return agentrepos.save(agent);
    }

    @Override
    public Agent findAgentByAgentcode(Long agentcode)
    {
        return agentrepos.findById(agentcode)
            .orElseThrow(() -> new EntityNotFoundException("Agent " + agentcode + "Not Found"));
    }
}
