package com.estsoft.projectdose.fcm.controller;

import com.estsoft.projectdose.fcm.dto.ApiResponseWrapper;
import com.estsoft.projectdose.fcm.dto.SuccessCode;
import com.estsoft.projectdose.fcm.dto.DeviceTokenDto;
import com.estsoft.projectdose.fcm.service.DeviceTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/device-token")
public class DeviceTokenController {

	private final DeviceTokenService deviceTokenService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponseWrapper<Object>> registerDeviceToken(@RequestBody @Validated DeviceTokenDto deviceTokenDto) {
		log.debug("[+] Registering device token");
		boolean result = deviceTokenService.registerDeviceToken(deviceTokenDto);

		ApiResponseWrapper<Object> response = ApiResponseWrapper.builder()
			.result(result ? 1 : 0)
			.resultCode(SuccessCode.OPERATION_SUCCESS.getStatus())
			.resultMsg(SuccessCode.OPERATION_SUCCESS.getMessage())
			.build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}