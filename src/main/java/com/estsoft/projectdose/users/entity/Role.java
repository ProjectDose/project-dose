package com.estsoft.projectdose.users.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
	ROLE_USER,
	ROLE_ADMIN;

	public GrantedAuthority getAuthority() {
		return new SimpleGrantedAuthority(this.name());
	}
}
