package com.bi.oa.spring.security.login.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "acounts",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "name"),
           @UniqueConstraint(columnNames = "email")
       })
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String name;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @NotBlank
  @Size(max = 20)
  private String gender;

  @NotBlank
  @Size(max = 20)
  private String birthdate;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "account_roles",
             joinColumns = @JoinColumn(name = "account_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public Account() {
  }

  public Account(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public Account(String name, String email, String password, String gender, String birthdate) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.gender = gender;
    this.birthdate = birthdate;
  }
}
