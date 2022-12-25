package org.sid.comptesqrseventsourcing.commonApi.commands;

public class CreateAccountCommand extends BaseCommand<String>{
    private double initialBalance;
    private String currency;
    public CreateAccountCommand(String id,double initialBalance, String currency) {
        super(id);
        this.currency=currency;
        this.initialBalance=initialBalance;
    }
}
