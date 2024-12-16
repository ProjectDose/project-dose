package com.estsoft.projectdose.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

	@NotBlank(message = "이름은 필수 입력값입니다.")
	private String name;

	@NotBlank(message = "닉네임은 필수 입력값입니다.")
	private String nickname;

	@NotBlank(message = "이메일은 필수 입력값입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	@Size(min = 6, max = 20, message = "특수문자, 대문자, 소문자 포함 6~20자리를 입력해주세요.")
	private String password;
}
