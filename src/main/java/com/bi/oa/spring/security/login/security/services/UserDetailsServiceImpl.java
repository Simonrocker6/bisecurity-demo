package com.bi.oa.spring.security.login.security.services;

import com.bi.oa.spring.security.login.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bi.oa.spring.security.login.repository.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  AccountRepository accountRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    Account account = accountRepository.findByName(name)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + name));

    return UserDetailsImpl.build(account);
  }

}
