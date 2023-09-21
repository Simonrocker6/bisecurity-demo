package com.bi.oa.spring.security.login.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class AddRequest {
	@NotBlank
	private String username;

	private String password;

	private String email;

	private String gender;

	private String birthdate;

	private Set<String> role;
}
