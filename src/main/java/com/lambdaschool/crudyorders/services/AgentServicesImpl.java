package com.lambdaschool.crudyorders.services;


import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "agentservices")
public class AgentServicesImpl implements AgentServices{
    @Autowired
    private AgentRepository agentrepos;




    @Override
    public Agent findAgentById(long id) throws EntityNotFoundException {
        return agentrepos.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("agent" +id + "not found"));


    }
}

