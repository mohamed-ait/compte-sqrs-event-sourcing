package org.sid.customerquerysid.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.sid.customerquerysid.entities.Customer;
import org.sid.customerquerysid.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CustomerQueryHandler {

    private CustomerRepository repository;

    @QueryHandler
    public List<Customer> on(GetAllCustomersQuery query){
        return repository.findAll();
    }

    @QueryHandler
    public Customer on(GetCustomerByIdQuery query){
        return repository.findById(query.getId()).get();
    }}
