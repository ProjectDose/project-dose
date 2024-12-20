package com.estsoft.projectdose.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AiProxyController {

	private final String API_URL = "https://kdt-api-function.azurewebsites.net/api/v1/question";
	private final String CLIENT_ID = "2f7a92a8-3c31-4de8-b06e-4b9cccf0cb0b";

	@GetMapping("/api/ai/proxy")
	public ResponseEntity<String> proxy(@RequestParam String content) {
		RestTemplate restTemplate = new RestTemplate();
		String requestUrl = String.format("%s?content=%s&client_id=%s", API_URL, content, CLIENT_ID);
		String response = restTemplate.getForObject(requestUrl, String.class);
		return ResponseEntity.ok(response);
	}
}
