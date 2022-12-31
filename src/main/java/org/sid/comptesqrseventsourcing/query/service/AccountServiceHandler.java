package org.sid.comptesqrseventsourcing.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.sid.comptesqrseventsourcing.commonApi.enums.AccountStatus;
import org.sid.comptesqrseventsourcing.commonApi.enums.OperationType;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountActivatedEvent;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountCreatedEvent;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountCreditedEvent;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountDebitedEvent;
import org.sid.comptesqrseventsourcing.commonApi.queries.GetAllAcountsQuery;
import org.sid.comptesqrseventsourcing.query.entities.Account;
import org.sid.comptesqrseventsourcing.query.entities.Operation;
import org.sid.comptesqrseventsourcing.query.repositories.AccountRepository;
import org.sid.comptesqrseventsourcing.query.repositories.OperationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class AccountServiceHandler {
    AccountRepository accountRepository;
    OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("*************************");
        log.info("AccountCreatedEvent received");
        Account account=new Account();
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setBalance(event.getInitialBalance());
        account.setStatus(event.getStatus());
        accountRepository.save(account);

    }
    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("*************************");
        log.info("AccountActivatedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    //debit operation
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("*************************");
        log.info("AccountDebitedEvent received");
        Operation operation=new Operation();
        Account account=accountRepository.findById(event.getId()).get();
        account.setBalance(account.getBalance()-event.getAmount());
        operation.setAmount(event.getAmount());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operation.setDate(new Date());
        operationRepository.save(operation);
        account.setCurrency(event.getCurrency());
        accountRepository.save(account);
    }
    //credit operation
    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("*************************");
        log.info("AccountCreditedEvent received");
        Operation operation=new Operation();
        Account account=accountRepository.findById(event.getId()).get();
        account.setBalance(account.getBalance()+event.getAmount());
        operation.setAmount(event.getAmount());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operation.setDate(new Date());
        operationRepository.save(operation);
        account.setCurrency(event.getCurrency());
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAcountsQuery query){
        return accountRepository.findAll();
    }
}
