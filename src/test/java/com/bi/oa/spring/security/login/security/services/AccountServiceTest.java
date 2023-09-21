package com.bi.oa.spring.security.login.security.services;

import com.bi.oa.spring.security.login.models.Account;
import com.bi.oa.spring.security.login.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository mockAccountRepository;

    private AccountService accountServiceUnderTest;

    @BeforeEach
    void setUp() {
        accountServiceUnderTest = new AccountService();
        accountServiceUnderTest.accountRepository = mockAccountRepository;
    }

    @Test
    void testUpdate() {
        // Setup
        final Account account = new Account("name", "email", "password", "gender", "birthdate");

        // Configure AccountRepository.findByName(...).
        final Optional<Account> account1 = Optional.of(new Account("name", "email", "password", "gender", "birthdate"));
        when(mockAccountRepository.findByName("name")).thenReturn(account1);

        // Configure AccountRepository.save(...).
        final Account account2 = new Account("name", "email", "password", "gender", "birthdate");
        when(mockAccountRepository.save(new Account("name", "email", "password", "gender", "birthdate")))
                .thenReturn(account2);

        // Run the test
        final boolean result = accountServiceUnderTest.update(account);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testUpdate_AccountRepositoryFindByNameReturnsAbsent() {
        // Setup
        final Account account = new Account("name", "email", "password", "gender", "birthdate");
        when(mockAccountRepository.findByName("name")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> accountServiceUnderTest.update(account)).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void testAdd() {
        // Setup
        final Account account = new Account("name", "email", "password", "gender", "birthdate");

        // Run the test
        accountServiceUnderTest.add(account);

        // Verify the results
        verify(mockAccountRepository).save(new Account("name", "email", "password", "gender", "birthdate"));
    }

    @Test
    void testSave() {
        // Setup
        final Account account = new Account("name", "email", "password", "gender", "birthdate");

        // Run the test
        accountServiceUnderTest.save(account);

        // Verify the results
        verify(mockAccountRepository).save(new Account("name", "email", "password", "gender", "birthdate"));
    }

    @Test
    void testDelete() {
        // Setup
        final Account account = new Account("name", "email", "password", "gender", "birthdate");

        // Run the test
        final boolean result = accountServiceUnderTest.delete(account);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockAccountRepository).delete(new Account("name", "email", "password", "gender", "birthdate"));
    }
}
