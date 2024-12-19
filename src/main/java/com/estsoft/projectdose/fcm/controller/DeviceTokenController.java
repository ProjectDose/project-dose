package com.estsoft.projectdose.fcm.controller;

import java.util.Optional;

import com.estsoft.projectdose.fcm.dto.ApiResponseWrapper;
import com.estsoft.projectdose.fcm.dto.SuccessCode;
import com.estsoft.projectdose.fcm.dto.DeviceTokenDto;
import com.estsoft.projectdose.fcm.service.DeviceTokenService;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/device-token")
public class DeviceTokenController {

	private final DeviceTokenService deviceTokenService;
	private final UsersRepository usersRepository;

	@PostMapping("/register")
	public ResponseEntity<ApiResponseWrapper<Object>> registerToken(@RequestBody DeviceTokenDto deviceTokenDto) {
		Optional<Users> userOptional = usersRepository.findByEmail(deviceTokenDto.getEmail());

		if (userOptional.isPresent()) {
			deviceTokenDto.setUserId(userOptional.get().getId());
			boolean result = deviceTokenService.registerDeviceToken(deviceTokenDto);

			String message = result ? "FCM 토큰 등록 성공" : "이미 등록된 토큰입니다.";
			ApiResponseWrapper<Object> response = ApiResponseWrapper.builder()
				.result(result ? 1 : 0)
				.resultCode(SuccessCode.OPERATION_SUCCESS.getStatus())
				.resultMsg(message)
				.build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


}