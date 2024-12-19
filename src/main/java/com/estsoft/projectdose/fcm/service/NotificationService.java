package com.estsoft.projectdose.fcm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

	private final RestTemplate restTemplate = new RestTemplate();

	public void sendNotification(String token, String title, String body) {
		String url = "/api/v1/fcm/sendMessage";

		Map<String, String> payload = new HashMap<>();
		payload.put("token", token);
		payload.put("title", title);
		payload.put("body", body);

		restTemplate.postForObject(url, payload, String.class);
	}
}
