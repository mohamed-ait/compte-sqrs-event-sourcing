package org.sid.customerquerysid.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ord.sid.coreapi.queries.GetAllCustomersQuery;
import ord.sid.coreapi.queries.GetCustomerByIdQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.sid.customerquerysid.entities.Customer;
import org.sid.customerquerysid.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CustomerQueryHandler {

    private CustomerRepository customerRepository;

    @QueryHandler
    public List<Customer> on(GetAllCustomersQuery query){
        return customerRepository.findAll();
    }

    @QueryHandler
    public Customer on(GetCustomerByIdQuery query){
        return customerRepository.findById(query.getId()).get();
    }}
