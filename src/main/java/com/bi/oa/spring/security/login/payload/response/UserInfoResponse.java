package com.bi.oa.spring.security.login.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {
	private Long id;
	private String name;
	private String email;
	private List<String> roles;

}
