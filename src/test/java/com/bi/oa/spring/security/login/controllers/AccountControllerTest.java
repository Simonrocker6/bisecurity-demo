package com.bi.oa.spring.security.login.controllers;

import com.bi.oa.spring.security.login.models.Account;
import com.bi.oa.spring.security.login.models.ERole;
import com.bi.oa.spring.security.login.models.Role;
import com.bi.oa.spring.security.login.repository.AccountRepository;
import com.bi.oa.spring.security.login.repository.RoleRepository;
import com.bi.oa.spring.security.login.security.jwt.JwtUtils;
import com.bi.oa.spring.security.login.security.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager mockAuthenticationManager;
    @MockBean
    private AccountRepository mockAccountRepository;
    @MockBean
    private RoleRepository mockRoleRepository;
    @MockBean
    private PasswordEncoder mockEncoder;
    @MockBean
    private JwtUtils mockJwtUtils;
    @MockBean
    private AccountService mockAccountService;

    @Test
    void testUpdateAccount() throws Exception {
        // Setup
        when(mockAccountRepository.existsByName("name")).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/account/update")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .with(user("username"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(403);
    }

    @Test
    void testUpdateAccount_AccountRepositoryReturnsTrue() throws Exception {
        // Setup
        when(mockAccountRepository.existsByName("name")).thenReturn(true);
        when(mockEncoder.encode("password")).thenReturn("password");

        // Configure RoleRepository.findByName(...).
        final Role role1 = new Role();
        role1.setId(0);
        role1.setName(ERole.ROLE_USER);
        final Optional<Role> role = Optional.of(role1);
        when(mockRoleRepository.findByName(ERole.ROLE_USER)).thenReturn(role);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/account/update")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .with(user("username"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(403);
    }
}
