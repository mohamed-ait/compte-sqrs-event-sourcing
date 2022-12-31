package org.sid.customerquerysid.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ord.sid.coreapi.events.CustomerCreatedEvent;
import ord.sid.coreapi.events.CustomerDeletedEvent;
import ord.sid.coreapi.events.CustomerUpdatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.sid.customerquerysid.entities.Customer;
import org.sid.customerquerysid.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CustomerEventHandler {
    private CustomerRepository customerRepository;

    @EventHandler
    public void handle(CustomerCreatedEvent event){
        Customer customer = new Customer(
                event.getId(),
                event.getName(),
                event.getEmail()
        );
        customerRepository.save(customer);
    }

    @EventHandler
    public void handle(CustomerUpdatedEvent event){

        Customer customer = customerRepository.findById(event.getId()).get();
        customer.setEmail(event.getEmail());
        customer.setName(event.getName());
        customerRepository.save(customer);
        log.info("Customer updated ");
    }

    @EventHandler
    public void handle(CustomerDeletedEvent event){
        customerRepository.deleteById(event.getId());
        log.info("Customer deleted !");
    }
}
