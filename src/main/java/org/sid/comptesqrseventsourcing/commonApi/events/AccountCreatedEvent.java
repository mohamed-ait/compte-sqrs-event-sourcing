package org.sid.comptesqrseventsourcing.commonApi.events;

import lombok.Getter;

public class AccountCreatedEvent extends BaseEvent<String>{
    @Getter private double balance;
    @Getter private String currency;
    public AccountCreatedEvent(String id) {
        super(id);
    }
}
