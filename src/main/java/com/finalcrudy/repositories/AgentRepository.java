package com.finalcrudy.repositories;

import com.finalcrudy.models.Agent;
import org.springframework.data.repository.CrudRepository;


public interface AgentRepository
    extends CrudRepository<Agent, Long>
{
}