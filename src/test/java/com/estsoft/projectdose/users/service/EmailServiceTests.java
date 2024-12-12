package com.estsoft.projectdose.users.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.mockito.Mockito;

@SpringBootTest
public class EmailServiceTests {

	@Autowired
	private EmailService emailService;

	@Test
	public void testSendEmail() {
		String to = "test@example.com";
		String subject = "테스트 이메일";
		String body = "<h1>비밀번호 재설정 링크</h1><p>이 링크를 클릭하여 비밀번호를 재설정하세요.</p>";

		emailService.sendEmail(to, subject, body);
		System.out.println("이메일이 성공적으로 전송되었습니다.");
	}

	@TestConfiguration
	static class TestConfig {
		@Bean
		public EmailService emailService() {
			return Mockito.mock(EmailService.class);
		}
	}
}
