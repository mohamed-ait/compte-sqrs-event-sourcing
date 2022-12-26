package org.sid.comptesqrseventsourcing.query.repositories;

import org.sid.comptesqrseventsourcing.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<String, Account> {
}
