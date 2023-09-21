package com.bi.oa.spring.security.login.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class DeleteRequest {
	@NotBlank
	private String username;
}
