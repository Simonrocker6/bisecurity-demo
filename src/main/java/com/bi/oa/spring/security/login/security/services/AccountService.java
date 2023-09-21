package com.bi.oa.spring.security.login.security.services;

import com.bi.oa.spring.security.login.models.Account;
import com.bi.oa.spring.security.login.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  @Autowired
  AccountRepository accountRepository;

  public boolean  update(Account account) throws UsernameNotFoundException {
    Account originalAccount = accountRepository.findByName(account.getName())
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + account.getName()));
    account.setId(originalAccount.getId());
    Account savedAccount = accountRepository.save(account);
    return true;
  }

  public void  add(Account account) throws UsernameNotFoundException {
    accountRepository.save(account);
  }

  public void  save(Account account) throws UsernameNotFoundException {
    accountRepository.save(account);
  }

  public boolean  delete(Account account) throws UsernameNotFoundException {

    accountRepository.delete(account);
    return true;
  }

}
