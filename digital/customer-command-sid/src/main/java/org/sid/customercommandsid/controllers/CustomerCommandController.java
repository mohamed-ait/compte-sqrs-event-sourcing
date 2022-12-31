package org.sid.customercommandsid.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ord.sid.coreapi.commands.CreateCustomerCommand;
import ord.sid.coreapi.commands.DeleteCustomerCommand;
import ord.sid.coreapi.commands.UpdateCustomerCommand;
import ord.sid.coreapi.dtos.CreateCustomerRequestDTO;
import ord.sid.coreapi.dtos.UpdateCustomerRequestDTO;
import ord.sid.coreapi.events.CustomerCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/customers/commands")
public class CustomerCommandController {
    private CommandGateway commandGateway;
    @PostMapping(path = "/create")
    public CompletableFuture<String> newCustomer(@RequestBody CreateCustomerRequestDTO request){
        CompletableFuture<String> response = commandGateway.send(new CreateCustomerCommand(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getEmail()
        ));
        return response;
    }
    @PutMapping(path = "/update/{customerId}")
    public CompletableFuture<String> updateAccount(@PathVariable String customerId, @RequestBody UpdateCustomerRequestDTO requestDTO){
        CompletableFuture<String> commandResponse = commandGateway.send(new UpdateCustomerCommand(
                customerId,
                requestDTO.getName(),
                requestDTO.getEmail()
        ));
        return commandResponse;
    }

    @DeleteMapping(path = "/delete/{customerId}")
    public CompletableFuture<String> deleteAccount(@PathVariable String customerId){
        CompletableFuture<String> commandResponse = commandGateway.send(new DeleteCustomerCommand(
                customerId
        ));
        return commandResponse;
    }



}
