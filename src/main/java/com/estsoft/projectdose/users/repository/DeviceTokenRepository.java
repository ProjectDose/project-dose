package com.estsoft.projectdose.users.repository;

import java.util.Optional;

import com.estsoft.projectdose.users.entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
	Optional<DeviceToken> findByToken(String token);
	DeviceToken save(DeviceToken deviceToken);
}
