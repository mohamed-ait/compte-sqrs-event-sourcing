package org.sid.comptesqrseventsourcing.commonApi.events;

import lombok.Getter;
import org.sid.comptesqrseventsourcing.commonApi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String>{
    @Getter private AccountStatus status;

    public AccountActivatedEvent(String id,AccountStatus status) {
        super(id);
        this.status=status;
    }
}
