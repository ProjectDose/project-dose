package com.estsoft.projectdose.admin.dto;

import java.time.LocalDateTime;

import com.estsoft.projectdose.users.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserListResponse {
	private Long id;
	private String email;
	private String nickname;
	private String name;
	private LocalDateTime joinDate;
	private boolean isDeleted;
	private String role;

	public static UserListResponse from(Users user) {
		UserListResponse dto = new UserListResponse();
		dto.id = user.getId();
		dto.email = user.getEmail();
		dto.nickname = user.getNickname();
		dto.name = user.getName();
		dto.joinDate = user.getJoinDate();
		dto.isDeleted = user.isDeleted();
		dto.role = user.getRole().name();
		return dto;
	}
}