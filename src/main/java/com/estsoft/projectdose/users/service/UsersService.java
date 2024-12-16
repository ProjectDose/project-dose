package com.estsoft.projectdose.users.service;

import com.estsoft.projectdose.users.dto.PasswordFindRequest;
import com.estsoft.projectdose.users.dto.PasswordResetRequest;
import com.estsoft.projectdose.users.dto.SignUpRequest;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.entity.Role;
import com.estsoft.projectdose.users.repository.UsersRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class UsersService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final JavaMailSender mailSender;

	public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
	}

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
		user.setJoinDate(LocalDate.now().atStartOfDay());
		user.setDeleted(false);
		user.setRole(Role.ROLE_USER);

		usersRepository.save(user);
	}

	public boolean checkEmail(String email) {
		return usersRepository.findByEmail(email).isEmpty();
	}

	public boolean checkNickname(String nickname) {
		return usersRepository.findByNickname(nickname).isEmpty();
	}

	@Transactional
	public void findPassword(PasswordFindRequest passwordFindRequest) {
		Users user = usersRepository.findByEmail(passwordFindRequest.getEmail())
			.orElseThrow(() -> new RuntimeException("가입된 이메일이 아닙니다."));

		String token = UUID.randomUUID().toString();
		LocalDateTime expiryTime = LocalDateTime.now().plusHours(1); // 1시간 만료 시간 설정

		user.setResetToken(token);
		user.setResetTokenExpiry(expiryTime);
		usersRepository.save(user);

		String resetLink = "http://project-dose.com/reset-password?token=" + token;
		String emailBody = "<h1>비밀번호 재설정 요청</h1>"
			+ "<p>아래 링크를 클릭하여 비밀번호를 재설정하세요. 링크는 1시간 동안 유효합니다:</p>"
			+ "<a href=\"" + resetLink + "\">비밀번호 재설정 링크</a>";

		try {
			sendEmail(user.getEmail(), "비밀번호 재설정 요청", emailBody);
		} catch (MessagingException e) {
			throw new RuntimeException("이메일 전송에 실패했습니다.");
		}
	}

	private void sendEmail(String to, String subject, String body) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);

		mailSender.send(message);
	}

	@Transactional
	public void resetPassword(PasswordResetRequest passwordResetRequest) {
		Users user = usersRepository.findByResetToken(passwordResetRequest.getToken())
			.orElseThrow(() -> new RuntimeException("유효하지 않은 토큰입니다."));

		if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("토큰이 만료되었습니다. 다시 요청해주세요.");
		}

		user.setPassword(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
		user.setResetToken(null);
		user.setResetTokenExpiry(null);
		usersRepository.save(user);
	}
}
