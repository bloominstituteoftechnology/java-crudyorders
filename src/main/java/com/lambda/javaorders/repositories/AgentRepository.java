package com.lambda.javaorders.repositories;

import com.lambda.javaorders.models.Agent;
import org.springframework.data.repository.CrudRepository;

public interface AgentRepository extends CrudRepository<Agent, Long>
{
}
