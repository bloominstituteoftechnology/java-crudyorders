package com.finalcrudy.services;

import com.finalcrudy.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import com.finalcrudy.models.Agent;
import com.finalcrudy.services.AgentServices;

@Transactional
@Service(value = "agentsService")
public class AgentServicesImpl
    implements AgentServices
{
    @Autowired
    private AgentRepository agentsrepos;

    @Override
    public Agent findAgentById(long id) throws
                                        EntityNotFoundException
    {
        return agentsrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Agent Id " + id + " Not Found"));
    }
}