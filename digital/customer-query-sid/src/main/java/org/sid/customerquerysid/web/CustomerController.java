package org.sid.customerquerysid.web;

import lombok.AllArgsConstructor;
import ord.sid.coreapi.queries.GetAllCustomersQuery;
import ord.sid.coreapi.queries.GetCustomerByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.sid.customerquerysid.entities.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerController {
    private QueryGateway queryGateway;

    @GetMapping("/customers")
    public List<Customer> customersList(){
        List<Customer> response = queryGateway.query(new GetAllCustomersQuery(), ResponseTypes.multipleInstancesOf(Customer.class)).join();
        return response;
    }

    @GetMapping("/customer/{id}")
    public Customer customerById(@PathVariable String id){
        Customer response = queryGateway.query(new GetCustomerByIdQuery(id), ResponseTypes.instanceOf(Customer.class)).join();
        return response;
    }
}
