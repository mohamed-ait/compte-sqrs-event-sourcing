package org.sid.comptesqrseventsourcing.commands.controllers;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.sid.comptesqrseventsourcing.commonApi.commands.CreateAccountCommand;
import org.sid.comptesqrseventsourcing.commonApi.dtos.CreateAccountRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@RestController
@RequestMapping("/commands/account")
public class AcountCommandController {
    private CommandGateway commandGateway;
    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
        UUID.randomUUID().toString(),
        request.getInitialBalance(),
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
}
