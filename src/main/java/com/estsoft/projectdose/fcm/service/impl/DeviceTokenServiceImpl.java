package com.estsoft.projectdose.fcm.service.impl;

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
		String generatedToken = UUID.randomUUID().toString();

		// 토큰 설정 (기존 값이 없는 경우)
		String token = deviceTokenDto.getToken() != null
			? deviceTokenDto.getToken()
			: generatedToken;

		DeviceToken deviceToken = DeviceToken.builder()
			.userId(deviceTokenDto.getUserId())
			.token(token)
			.build();

		deviceTokenRepository.save(deviceToken);
		return true;
	}
}