package org.sid.comptesqrseventsourcing.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.sid.comptesqrseventsourcing.commonApi.enums.AccountStatus;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountActivatedEvent;
import org.sid.comptesqrseventsourcing.commonApi.events.AccountCreatedEvent;
import org.sid.comptesqrseventsourcing.query.entities.Account;
import org.sid.comptesqrseventsourcing.query.repositories.AccountRepository;
import org.sid.comptesqrseventsourcing.query.repositories.OperationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        log.info("AccountCreatedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
}
