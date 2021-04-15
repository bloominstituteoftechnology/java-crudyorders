package com.lambdaschool.crudyorders.controller;//package com.lambdaschool.modelorders.controller;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agents")
public class AgentController {

  @Autowired
  private AgentServices agentServices;

  //  http://localhost:2019/agents/agent/9
  @GetMapping(value = "/agent/{agentid}",
      produces = "application/json")
  public ResponseEntity<?> findAgentById(
      @PathVariable long agentid){
    Agent rtnAgent = agentServices.findAgentById(agentid);
    return new ResponseEntity<>(rtnAgent, HttpStatus.OK);
  }
}
