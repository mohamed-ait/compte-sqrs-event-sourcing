package org.sid.comptesqrseventsourcing.commands.controllers;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.sid.comptesqrseventsourcing.commonApi.commands.CreateAccountCommand;
import org.sid.comptesqrseventsourcing.commonApi.commands.CreditAccountCommand;
import org.sid.comptesqrseventsourcing.commonApi.commands.DebitAccountCommand;
import org.sid.comptesqrseventsourcing.commonApi.dtos.CreateAccountRequestDTO;
import org.sid.comptesqrseventsourcing.commonApi.dtos.CreditAccountRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@AllArgsConstructor
@RestController
@RequestMapping("/commands/account")
public class AcountCommandController {
    EventStore eventStore;
    private CommandGateway commandGateway;
    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
        UUID.randomUUID().toString(),
        request.getInitialBalance(),
        request.getCurrency()));
        return commandResponse;
    }
    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()));
        return commandResponse;
    }
    @PutMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new DebitAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()));
        return commandResponse;
    }
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception){
     ResponseEntity<String> entity= new ResponseEntity<>(
            exception.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
    );
    return entity.toString();
    }
    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
     return eventStore.readEvents(accountId).asStream();
    }
}
