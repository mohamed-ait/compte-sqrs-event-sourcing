package org.sid.comptesqrseventsourcing.commands.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.sid.comptesqrseventsourcing.commonApi.commands.CreateAccountCommand;
import org.sid.comptesqrseventsourcing.commonApi.dtos.CreateAccountRequestDTO;
import org.sid.comptesqrseventsourcing.commonApi.enums.AccountStatus;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountActivatedEvent;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountCreatedEvent;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
        //required by AXON
    }
    @CommandHandler //pour écouter le bus
    public AccountAggregate(CreateAccountCommand command) {
        if(command.getInitialBalance()<0) throw  new RuntimeException("impossible de créer un compte avec un solde négative");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId= event.getId();
        this.balance= event.getInitialBalance();
        this.status=AccountStatus.CREATED;
        this.currency=event.getCurrency();
        AggregateLifecycle.apply(new AccountActivatedEvent(event.getId(),AccountStatus.ACTIVATED));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.status=event.getStatus();
    }
}
