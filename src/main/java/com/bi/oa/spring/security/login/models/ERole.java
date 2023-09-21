package com.bi.oa.spring.security.login.models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public enum ERole {
  ROLE_USER("user"),
  ROLE_MODERATOR("mod"),
  ROLE_ADMIN("admin");

  private static final Map<String, ERole> map = new HashMap<>();


  static {
    for (ERole e : values()) {
      map.put(e.value, e);
    }
  }

  private final String value;

  private ERole(String str) {
    this.value = str;
  }

  public String getValue() {
    return this.value;
  }

  public static ERole getERole(String str) {
    return map.get(str);
  }
}
