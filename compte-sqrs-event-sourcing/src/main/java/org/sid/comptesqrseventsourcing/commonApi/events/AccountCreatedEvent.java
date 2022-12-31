package org.sid.comptesqrseventsourcing.commonApi.events;

import lombok.Getter;
import org.sid.comptesqrseventsourcing.commonApi.enums.AccountStatus;

public class AccountCreatedEvent extends BaseEvent<String>{
    @Getter private double initialBalance;
    @Getter private String currency;
    @Getter private AccountStatus status;
    public AccountCreatedEvent(String id,double initialBalance,String currency,AccountStatus status) {
        super(id);
        this.initialBalance=initialBalance;
        this.currency=currency;
        this.status=status;
    }
}