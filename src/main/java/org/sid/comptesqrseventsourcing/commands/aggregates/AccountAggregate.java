package org.sid.comptesqrseventsourcing.commands.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.sid.comptesqrseventsourcing.commonApi.commands.CreateAccountCommand;
import org.sid.comptesqrseventsourcing.commonApi.dtos.CreateAccountRequestDTO;
import org.sid.comptesqrseventsourcing.commonApi.enums.AccountStatus;

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
        AggregateLifecycle.apply(new )
    }

}
