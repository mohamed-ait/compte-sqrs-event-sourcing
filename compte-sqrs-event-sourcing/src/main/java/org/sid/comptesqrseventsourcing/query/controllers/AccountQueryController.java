package org.sid.comptesqrseventsourcing.query.controllers;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.sid.comptesqrseventsourcing.commonApi.queries.GetAllAcountsQuery;
import org.sid.comptesqrseventsourcing.query.entities.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Slf4j
@Transactional
@RequestMapping("/query/accounts")
public class AccountQueryController {
    private QueryGateway queryGateway;
    @GetMapping("/allAccounts")
            public List<Account> accountList(){
        List<Account> response=queryGateway.query(new GetAllAcountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
        return response;
    }

}
