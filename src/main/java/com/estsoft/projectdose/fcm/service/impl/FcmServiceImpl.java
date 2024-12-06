package com.estsoft.projectdose.fcm.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.estsoft.projectdose.fcm.dto.FcmMessageDto;
import com.estsoft.projectdose.fcm.dto.FcmSendDto;
import com.estsoft.projectdose.fcm.service.FcmService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FcmServiceImpl implements FcmService {

	@Override
	public int sendMessageTo(FcmSendDto fcmSendDto) throws IOException {
		System.out.println("fcmSendDto :: " + fcmSendDto.toString());
		String message = makeMessage(fcmSendDto);
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters()
			.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + getAccessToken());

		HttpEntity<String> entity = new HttpEntity<>(message, headers);

		String API_URL = "https://fcm.googleapis.com/v1/projects/project-dose/messages:send";
		ResponseEntity<String> response = null;
		try {
			restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
			System.out.println(response.getStatusCode());
			return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
		} catch (Exception e) {
			log.error("[-] FCM 전송 오류 :: " + e.getMessage());
			log.error("[-] 오류 발생 토큰 :: [" + fcmSendDto.getToken() + "]");
			log.error("[-] 오류 발생 메시지 :: [" + fcmSendDto.getBody() + "]");
			return 0;
		}
	}

	private String getAccessToken() throws IOException {
		String firebaseConfigPath = "firebase/project-dose-firebase-key.json";

		GoogleCredentials googleCredentials = GoogleCredentials
			.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
			.createScoped(List.of("<https://www.googleapis.com/auth/cloud-platform>"));

		googleCredentials.refreshIfExpired();
		return googleCredentials.getAccessToken().getTokenValue();
	}

	private String makeMessage(FcmSendDto fcmSendDto) throws JsonProcessingException {

		ObjectMapper om = new ObjectMapper();
		FcmMessageDto fcmMessageDto = FcmMessageDto
			.builder()
			.message(FcmMessageDto.Message.builder()
				.token(fcmSendDto.getToken())
				.notification(FcmMessageDto.Notification.builder()
					.title(fcmSendDto.getTitle())
					.body(fcmSendDto.getBody())
					.image(null)
					.build()
				).build())
			.validateOnly(false)
			.build();

		return om.writeValueAsString(fcmMessageDto);
	}
}


