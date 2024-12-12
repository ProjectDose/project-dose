package com.estsoft.projectdose.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SignUpRequest {

	@NotBlank
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;

	@NotBlank
	@Size(min = 6, max = 20, message = "특수문자, 대문자, 소문자 포함 6~20자리를 입력해주세요.")
	private String password;

	@NotBlank
	private String nickname;

	@NotBlank
	private String name;

	// Getters and Setters
}

