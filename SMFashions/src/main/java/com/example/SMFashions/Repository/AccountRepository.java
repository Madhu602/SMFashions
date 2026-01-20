package com.example.SMFashions.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SMFashions.Entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByMobile(String mobile);

    Optional<Account> findByEmail(String email);

    boolean existsByMobile(String mobile);
}
