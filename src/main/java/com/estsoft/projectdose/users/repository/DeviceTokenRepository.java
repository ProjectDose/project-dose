package com.estsoft.projectdose.users.repository;

import com.estsoft.projectdose.users.entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
}
