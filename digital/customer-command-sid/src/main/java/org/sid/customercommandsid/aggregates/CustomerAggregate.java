package org.sid.customercommandsid.aggregates;

import lombok.extern.slf4j.Slf4j;
import ord.sid.coreapi.commands.CreateCustomerCommand;
import ord.sid.coreapi.commands.DeleteCustomerCommand;
import ord.sid.coreapi.commands.UpdateCustomerCommand;
import ord.sid.coreapi.events.CustomerCreatedEvent;
import ord.sid.coreapi.events.CustomerDeletedEvent;
import ord.sid.coreapi.events.CustomerUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import javax.jws.soap.SOAPMessageHandlers;

@Slf4j
@Aggregate
public class CustomerAggregate {


    @AggregateIdentifier
    private String id;

    private String name;
    private String email;

    public CustomerAggregate(){

    }

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand command){
        AggregateLifecycle.apply( new CustomerCreatedEvent(
                command.getId(),
                command.getName(),
                command.getEmail()
        ));
    }
    @EventSourcingHandler
    public void on(CustomerCreatedEvent event){
        this.id=event.getId();
        this.name= event.getName();
        this.email=event.getEmail();
    }

    @CommandHandler
    public void handle(UpdateCustomerCommand command){
        AggregateLifecycle.apply( new CustomerCreatedEvent(
                command.getId(),
                command.getName(),
                command.getEmail()
        ));
    }
    @EventSourcingHandler
    public void on(CustomerUpdatedEvent event){
        this.id=event.getId();
        this.email=event.getEmail();
        this.name=event.getName();
    }


    @CommandHandler
    public void handle(DeleteCustomerCommand command){
        AggregateLifecycle.apply( new CustomerDeletedEvent(
                command.getId()
        ));
    }
    @EventSourcingHandler
    public void on(CustomerDeletedEvent event){
        this.id=event.getId();
    }




}