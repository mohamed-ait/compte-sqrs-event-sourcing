package org.sid.comptesqrseventsourcing.commands.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.sid.comptesqrseventsourcing.commonApi.commands.CreateAccountCommand;
import org.sid.comptesqrseventsourcing.commonApi.commands.CreditAccountCommand;
import org.sid.comptesqrseventsourcing.commonApi.commands.DebitAccountCommand;
import org.sid.comptesqrseventsourcing.commonApi.enums.AccountStatus;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountActivatedEvent;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountCreatedEvent;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountCreditedEvent;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountDebitedEvent;
import org.sid.comptesqrseventsourcing.commonApi.exceptions.AmountNegativeException;
import org.sid.comptesqrseventsourcing.commonApi.exceptions.BalanceNotSufficientException;

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
    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){
if(creditAccountCommand.getAmount()<0) throw new AmountNegativeException("amount should not be negative");
AggregateLifecycle.apply(new AccountCreditedEvent(
        creditAccountCommand.getId(),
        creditAccountCommand.getAmount(),
        creditAccountCommand.getCurrency()
));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance+=event.getAmount();
    }
    public void handler(DebitAccountCommand debitAccountCommand){
        if(debitAccountCommand.getAmount()<0) throw new AmountNegativeException("amount should not be negative");
        if(this.balance<debitAccountCommand.getAmount()) throw new BalanceNotSufficientException("balance not sufficient exception --->"+balance),
        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance+=event.getAmount();
    }
}
