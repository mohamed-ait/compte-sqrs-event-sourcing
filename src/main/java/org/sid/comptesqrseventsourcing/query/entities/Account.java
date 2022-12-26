package org.sid.comptesqrseventsourcing.query.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.comptesqrseventsourcing.commonApi.enums.AccountStatus;

import javax.persistence.*;
import java.util.Collection;
@Data @NoArgsConstructor @AllArgsConstructor @Entity
public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    @OneToMany(mappedBy = "acount")
    private Collection<Operation> operations;

}
