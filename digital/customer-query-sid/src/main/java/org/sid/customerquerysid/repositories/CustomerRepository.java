package org.sid.customerquerysid.repositories;

import org.sid.customerquerysid.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
