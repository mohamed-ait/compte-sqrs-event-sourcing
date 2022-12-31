package org.sid.comptesqrseventsourcing.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.comptesqrseventsourcing.commonApi.enums.OperationType;

import javax.persistence.*;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Account account;


}
