package org.sid.comptesqrseventsourcing.commonApi.events;

import lombok.Getter;

public class AccountCritedEvent extends BaseEvent<String>{
    @Getter
    private double amount;
    @Getter private String currency;

    public AccountCritedEvent(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }

}
