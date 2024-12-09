package com.estsoft.projectdose.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {

	@NotBlank
	private String token;

	@NotBlank
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)[A-Za-z\\d\\W]{6,20}$",
		message = "특수문자, 대문자, 소문자 포함 6~20자리를 입력해주세요.")
	private String newPassword;
}

