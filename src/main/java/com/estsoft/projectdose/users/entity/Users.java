package com.estsoft.projectdose.users.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, unique = true)
	private Long id;

	@Column(name = "email", nullable = false, unique = true, length = 100)
	private String email;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "nickname", nullable = false, length = 100)
	private String nickname;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@CreatedDate
	@Column(name = "join_date", nullable = false)
	private LocalDateTime joinDate;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isDeleted;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "reset_token", length = 100)
	private String resetToken;

	@Column(name = "reset_token_expiry")
	private LocalDateTime resetTokenExpiry;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DeviceToken> deviceTokens;
}
