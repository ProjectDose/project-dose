package com.estsoft.projectdose.fcm.repository;

import com.estsoft.projectdose.users.entity.Users;

public interface DeviceTokenRepository {
	java.util.Optional<Users> findByEmail(String email);

	Users save(Users user);
}
