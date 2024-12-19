package com.estsoft.projectdose.users.repository;

import java.util.List;
import java.util.Optional;

import com.estsoft.projectdose.users.entity.DeviceToken;
import com.estsoft.projectdose.users.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
	Optional<DeviceToken> findByUserAndToken(Users user,String token);
	List<DeviceToken> findAllByUser_Id(Long userId);
	DeviceToken save(DeviceToken deviceToken);

}
