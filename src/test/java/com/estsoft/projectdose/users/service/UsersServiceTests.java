package com.estsoft.projectdose.users.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersServiceTests {

	@Autowired
	private UsersService usersService;

	@Test
	public void testResetPassword() {
		System.out.println("UsersService 비밀번호 재설정 테스트 실행");
	}
}
