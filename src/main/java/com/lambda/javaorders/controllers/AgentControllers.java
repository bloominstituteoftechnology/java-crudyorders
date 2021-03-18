package com.lambda.javaorders.controllers;

import com.lambda.javaorders.models.Agent;
import com.lambda.javaorders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentControllers
{
    @Autowired
    private AgentServices agentServices;
    // Returns a single Agent
    // http://localhost:2019/agents/agent/9
    @GetMapping(value = "/agent/{agentid}", produces = "application/json")
    public ResponseEntity<?> findAgentById(@PathVariable long agentid)
    {
        Agent a = agentServices.findAgentById(agentid);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }
}
