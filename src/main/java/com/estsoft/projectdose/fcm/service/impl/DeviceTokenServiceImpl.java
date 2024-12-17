package com.estsoft.projectdose.fcm.service.impl;

import java.util.Optional;
import java.util.UUID;

import com.estsoft.projectdose.fcm.dto.DeviceTokenDto;
import com.estsoft.projectdose.fcm.service.DeviceTokenService;
import com.estsoft.projectdose.users.entity.DeviceToken;
import com.estsoft.projectdose.users.repository.DeviceTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceTokenServiceImpl implements DeviceTokenService {

	private final DeviceTokenRepository deviceTokenRepository;

	@Override
	public boolean registerDeviceToken(DeviceTokenDto deviceTokenDto) {
		// UUID 자동 생성
		Optional<DeviceToken> existingToken = deviceTokenRepository.findByToken(deviceTokenDto.getToken());

		// 이미 등록된 토큰이면 처리 종료
		if (existingToken.isPresent()) {
			System.out.println("이미 등록된 FCM 토큰입니다.");
			return false;
		}

		DeviceToken deviceToken = DeviceToken.builder()
			.userId(deviceTokenDto.getUserId())
			.token(deviceTokenDto.getToken())
			.build();

		deviceTokenRepository.save(deviceToken);
		return true;
	}
}