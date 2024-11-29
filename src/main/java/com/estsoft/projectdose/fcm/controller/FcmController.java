package com.estsoft.projectdose.fcm.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estsoft.projectdose.fcm.dto.ApiResponseWrapper;
import com.estsoft.projectdose.fcm.dto.FcmSendDto;
import com.estsoft.projectdose.fcm.dto.SuccessCode;
import com.estsoft.projectdose.fcm.service.FcmService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/fcm")
public class FcmController {

	private final FcmService fcmService;

	public FcmController(FcmService fcmService) {
		this.fcmService = fcmService;
	}

	@PostMapping("/api/v1/fcm/sendMessage")
	public ResponseEntity<ApiResponseWrapper<Object>> pushMessage(@RequestBody @Validated FcmSendDto fcmSendDto)
		throws IOException {
		log.debug("[+] 푸시 메시지를 전송합니다. ");
		int result = fcmService.sendMessageTo(fcmSendDto);

		ApiResponseWrapper<Object> arw = ApiResponseWrapper
			.builder()
			.result(result)
			.resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
			.resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
		return new ResponseEntity<>(arw, HttpStatus.OK);
	}
}
