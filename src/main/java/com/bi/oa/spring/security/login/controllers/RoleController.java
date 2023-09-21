package com.bi.oa.spring.security.login.controllers;

import com.bi.oa.spring.security.login.models.Account;
import com.bi.oa.spring.security.login.models.ERole;
import com.bi.oa.spring.security.login.models.Role;
import com.bi.oa.spring.security.login.payload.request.AddRequest;
import com.bi.oa.spring.security.login.payload.request.DeleteRequest;
import com.bi.oa.spring.security.login.payload.request.RoleRequest;
import com.bi.oa.spring.security.login.payload.request.UpdateRequest;
import com.bi.oa.spring.security.login.payload.response.MessageResponse;
import com.bi.oa.spring.security.login.repository.AccountRepository;
import com.bi.oa.spring.security.login.repository.RoleRepository;
import com.bi.oa.spring.security.login.security.jwt.JwtUtils;
import com.bi.oa.spring.security.login.security.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/role")
public class RoleController {
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


  /**
   * Add roles to proceed. todo: optimzie whole flow.
   */
  @PostMapping("/addRoles")
  public ResponseEntity<?> add() {
    List<Role> roles = roleRepository.findAll();
    if (roles.size() == 3) {
      return ResponseEntity.ok("Roles already added.");
    }
    roleRepository.deleteAll();
    List<Role> addRoles = new ArrayList<>();
    for (ERole e : ERole.values()) {
      Role role = new Role();
      role.setName(e);
      addRoles.add(role);
    }
    roleRepository.saveAll(addRoles);
    return ResponseEntity.ok("Roles add succeed.");
  }

  @GetMapping("/listAll")
  public ResponseEntity<?> listAll() {
    List<Role> roles = roleRepository.findAll();
    return ResponseEntity.ok(roles);
  }
}
