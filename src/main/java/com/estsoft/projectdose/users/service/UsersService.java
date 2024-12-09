package com.estsoft.projectdose.users.service;

import com.estsoft.projectdose.users.dto.PasswordFindRequest;
import com.estsoft.projectdose.users.dto.PasswordResetRequest;
import com.estsoft.projectdose.users.dto.SignUpRequest;
import com.estsoft.projectdose.users.entity.Role;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 이메일 중복 체크
	public boolean checkEmail(String email) {
		return usersRepository.findByEmail(email).isEmpty();
	}

	// 닉네임 중복 체크
	public boolean checkNickname(String nickname) {
		return usersRepository.findByNickname(nickname).isEmpty();
	}

	// 회원가입
	@Transactional
	public void signUp(SignUpRequest signUpRequest) {
		if (!checkEmail(signUpRequest.getEmail())) {
			throw new RuntimeException("이미 존재하는 이메일입니다.");
		}
		if (!checkNickname(signUpRequest.getNickname())) {
			throw new RuntimeException("중복된 닉네임입니다.");
		}

		Users user = new Users();
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setNickname(signUpRequest.getNickname());
		user.setName(signUpRequest.getName());
		user.setJoindate(LocalDate.now());
		user.setDeleted(false);
		user.setRole(Role.ROLE_USER);

		usersRepository.save(user);
	}

	// 비밀번호 찾기
	@Transactional
	public void findPassword(PasswordFindRequest passwordFindRequest) {
		Users user = usersRepository.findByEmail(passwordFindRequest.getEmail())
			.orElseThrow(() -> new RuntimeException("가입된 이메일이 아닙니다."));

		String token = UUID.randomUUID().toString();
		user.setResetToken(token);
		usersRepository.save(user);

	}


	// 비밀번호 재설정
	@Transactional
	public void resetPassword(PasswordResetRequest passwordResetRequest) {
		Users user = usersRepository.findByResetToken(passwordResetRequest.getToken())
			.orElseThrow(() -> new RuntimeException("유효하지 않은 토큰입니다."));

		user.setPassword(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
		user.setResetToken(null);
		usersRepository.save(user);
	}


}
