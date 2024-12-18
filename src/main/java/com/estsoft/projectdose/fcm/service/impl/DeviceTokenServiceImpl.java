package com.estsoft.projectdose.fcm.service.impl;

import java.util.Optional;
import java.util.UUID;

import com.estsoft.projectdose.fcm.dto.DeviceTokenDto;
import com.estsoft.projectdose.fcm.service.DeviceTokenService;
import com.estsoft.projectdose.users.entity.DeviceToken;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.repository.DeviceTokenRepository;
import com.estsoft.projectdose.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceTokenServiceImpl implements DeviceTokenService {

	private final DeviceTokenRepository deviceTokenRepository;
	private final UsersRepository usersRepository;

	@Override
	@Transactional
	public boolean registerDeviceToken(DeviceTokenDto deviceTokenDto) {
		Optional<Users> userOptional = usersRepository.findByEmail(deviceTokenDto.getEmail());

		if (userOptional.isPresent()) {
			Users user = userOptional.get();

			Optional<DeviceToken> existingToken = deviceTokenRepository.findByUserAndToken(user, deviceTokenDto.getToken());

			if (existingToken.isPresent()) {
				log.info("이미 등록된 FCM 토큰입니다.");
				return false;
			}

			DeviceToken deviceToken = DeviceToken.builder()
				.user(user)
				.token(deviceTokenDto.getToken())
				.build();

			deviceTokenRepository.save(deviceToken);
			return true;
		} else {
			log.warn("유효하지 않은 사용자입니다.");
			return false;
		}
	}



	// @Transactional
	// public boolean registerDeviceToken(DeviceTokenDto deviceTokenDto) {
	// 	Optional<Users> userOptional = usersRepository.findById(deviceTokenDto.getUserId());
	//
	// 	if (userOptional.isPresent()) {
	// 		Users user = userOptional.get();
	//
	// 		// 기존 토큰이 있는지 확인
	// 		Optional<DeviceToken> existingToken = deviceTokenRepository.findByUserAndToken(user, deviceTokenDto.getToken());
	//
	// 		if (existingToken.isPresent()) {
	// 			log.info("이미 등록된 FCM 토큰입니다.");
	// 			return false;
	// 		}
	//
	// 		// 새 토큰 등록
	// 		DeviceToken deviceToken = DeviceToken.builder()
	// 			.user(user)
	// 			.token(deviceTokenDto.getToken())
	// 			.build();
	//
	// 		deviceTokenRepository.save(deviceToken);
	// 		return true;
	// 	} else {
	// 		log.warn("유효하지 않은 사용자입니다.");
	// 		return false;
	// 	}
	// }
}