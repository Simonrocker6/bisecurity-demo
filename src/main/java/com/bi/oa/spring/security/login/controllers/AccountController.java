package com.bi.oa.spring.security.login.controllers;

import com.bi.oa.spring.security.login.models.Account;
import com.bi.oa.spring.security.login.models.ERole;
import com.bi.oa.spring.security.login.models.Role;
import com.bi.oa.spring.security.login.payload.request.AddRequest;
import com.bi.oa.spring.security.login.payload.request.DeleteRequest;
import com.bi.oa.spring.security.login.payload.request.LoginRequest;
import com.bi.oa.spring.security.login.payload.request.UpdateRequest;
import com.bi.oa.spring.security.login.payload.response.MessageResponse;
import com.bi.oa.spring.security.login.payload.response.UserInfoResponse;
import com.bi.oa.spring.security.login.repository.AccountRepository;
import com.bi.oa.spring.security.login.repository.RoleRepository;
import com.bi.oa.spring.security.login.security.jwt.JwtUtils;
import com.bi.oa.spring.security.login.security.services.AccountService;
import com.bi.oa.spring.security.login.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/account")
public class AccountController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  AccountService accountService;

  @PostMapping("/update")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateAccount(@Valid @RequestBody UpdateRequest updateRequest) {
    if (!accountRepository.existsByName(updateRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username Not Found!"));
    }

    Account account = new Account(updateRequest.getUsername(),
                         updateRequest.getEmail(),
                         encoder.encode(updateRequest.getPassword()),
            updateRequest.getGender(),
            updateRequest.getBirthdate());

    account.setRoles(getRoles(updateRequest.getRole()));
    accountService.update(account);

    return ResponseEntity.ok(new MessageResponse("Account updated successfully!"));
  }

  @PostMapping("/add")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> addAccount(@Valid @RequestBody AddRequest addRequest) {
    if (accountRepository.existsByName(addRequest.getUsername()) ||
            accountRepository.existsByEmail(addRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username Found!"));
    }

    Account account = new Account(addRequest.getUsername(),
            addRequest.getEmail(),
            encoder.encode(addRequest.getPassword()),
            addRequest.getGender(),
            addRequest.getBirthdate());

    account.setRoles(getRoles(addRequest.getRole()));
    accountService.save(account);
    return ResponseEntity.ok(new MessageResponse("Account added successfully!"));
  }

  @GetMapping("/listAll")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> listAll() {
    List<Account> accounts = accountRepository.findAll();
    return ResponseEntity.ok(accounts);
  }

  @PostMapping("/delete")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deleteAccount(@Valid @RequestBody DeleteRequest deleteRequest) {
    Account account = accountRepository.findByName(deleteRequest.getUsername()).orElseThrow(() ->
            new UsernameNotFoundException("User Not Found with username: " + deleteRequest.getUsername()));

    accountService.delete(account);

    return ResponseEntity.ok(new MessageResponse("Account deleted successfully!"));
  }

  private  Set<Role> getRoles(Set<String> strRoles) {
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
          case "mod":
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);

            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }
    return roles;
  }
}
