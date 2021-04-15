package com.lambdaschool.crudyorders.services;


import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.models.Customer;
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

    @Override
    public Agent save(Agent agent) {
        Agent newAgent = new Agent();

        //POST -> new resource
        //PUT -> replace existing resource
        if (agent.getAgentcode() != 0) {
            agentrepos.findById(agent.getAgentcode())
                    .orElseThrow(() -> new EntityNotFoundException("Agent " + agent.getAgentcode() + " not found!"));
            newAgent.setAgentcode(agent.getAgentcode());
        }

        newAgent.setAgentname(agent.getAgentname());
        newAgent.setWorkingarea(agent.getWorkingarea());
        newAgent.setPhone(agent.getPhone());
        newAgent.setCountry(agent.getCountry());


        //OneToMany -> new resources that arent in the database yet
        newAgent.getCustomers().clear();
        for (Customer c : agent.getCustomers()) {
            Customer customer = new Customer();
            customer.setAgent(c.getAgent());
            customer.setCustname(c.getCustname());
            customer.setCustcity(c.getCustcity());
            customer.setCustcountry(c.getCustcountry());
            customer.setGrade(c.getGrade());
            customer.setOpeningamt(c.getOpeningamt());
            customer.setReceiveamt(c.getReceiveamt());
            customer.setPaymentamt(c.getPaymentamt());
            customer.setOutstandingamt(c.getOutstandingamt());
            customer.setPhone(c.getPhone());

            customer.setAgent(newAgent);

            newAgent.getCustomers().add(customer);
        }



        return agentrepos.save(newAgent);
    }

}


