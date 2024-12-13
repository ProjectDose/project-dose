package com.estsoft.projectdose.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordFindRequest {

	@NotBlank
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;
}