package org.sid.comptesqrseventsourcing.commonApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitAccountRequestDTO {
    private String accountId;
    private double amount;
    private String currency;
}
