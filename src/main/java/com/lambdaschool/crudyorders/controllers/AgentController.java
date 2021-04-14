package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentController {
    @Autowired
    private AgentServices agentServices;





    @GetMapping(value = "/agents/agent/{agentid}",produces = "application/json")
    public ResponseEntity<?> findAgentById(@PathVariable Long agentid){
        Agent c = agentServices.findAgentById(agentid);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

}
