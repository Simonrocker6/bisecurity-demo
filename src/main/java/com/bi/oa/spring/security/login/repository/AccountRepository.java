package com.bi.oa.spring.security.login.repository;

import java.util.Optional;

import com.bi.oa.spring.security.login.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByName(String name);

  Boolean existsByName(String name);

  Boolean existsByEmail(String email);
}
