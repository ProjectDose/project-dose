package com.estsoft.projectdose.users.service;

import com.estsoft.projectdose.users.dto.PasswordFindRequest;
import com.estsoft.projectdose.users.dto.PasswordResetRequest;
import com.estsoft.projectdose.users.dto.SignUpRequest;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.entity.Role;
import com.estsoft.projectdose.users.repository.DeviceTokenRepository;
import com.estsoft.projectdose.users.repository.UsersRepository;
import com.estsoft.projectdose.users.entity.DeviceToken;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersService {

	private final DeviceTokenRepository deviceTokenRepository;
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final JavaMailSender mailSender;

	public UsersService(DeviceTokenRepository deviceTokenRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
		this.deviceTokenRepository = deviceTokenRepository;
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
	}

	@Transactional
	public void signUp(SignUpRequest signUpRequest) {
		if (!usersRepository.findByEmail(signUpRequest.getEmail()).isEmpty()) {
			throw new RuntimeException("이미 존재하는 이메일입니다.");
		}

		if (!usersRepository.findByNickname(signUpRequest.getNickname()).isEmpty()) {
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

	@Value("${BASE_URL}")
	private String baseUrl;

	@Transactional
	public void findPassword(PasswordFindRequest passwordFindRequest) {
		Users user = usersRepository.findByEmail(passwordFindRequest.getEmail())
			.orElseThrow(() -> new RuntimeException("가입된 이메일이 아닙니다."));

		String token = UUID.randomUUID().toString();
		LocalDateTime expiryTime = LocalDateTime.now().plusHours(1);

		user.setResetToken(token);
		user.setResetTokenExpiry(expiryTime);
		usersRepository.save(user);

		String resetLink = baseUrl + "/api/auth/reset-password?token=" + token;

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

	@Transactional
	public boolean validateResetToken(String token) {
		return usersRepository.findByResetToken(token)
			.map(user -> {
				if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
					return false;
				}
				return true;
			})
			.orElse(false);
	}

	public Users findUserByResetToken(String token) {
		return usersRepository.findByResetToken(token)
			.orElseThrow(() -> new RuntimeException("유효하지 않은 토큰입니다."));
	}

	public boolean isSameAsOldPassword(Users user, String newPassword) {
		return passwordEncoder.matches(newPassword, user.getPassword());
	}

	public Users findUserByEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new RuntimeException("이메일이 누락되었습니다.");
		}
		return usersRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("이메일로 사용자를 찾을 수 없습니다: " + email));
	}

	@Transactional
	public void updateUser(Long userId, Map<String, String> updates) {
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		if (updates.containsKey("nickname")) {
			String newNickname = updates.get("nickname");

			if (user.getNickname().equals(newNickname)) {
				throw new RuntimeException("이미 설정한 닉네임입니다.");
			}

			if (usersRepository.findByNickname(newNickname).isPresent()) {
				throw new RuntimeException("존재하는 닉네임입니다.");
			}

			user.setNickname(newNickname);
		}

		if (updates.containsKey("currentPassword") && updates.containsKey("newPassword")) {
			String currentPassword = updates.get("currentPassword");
			String newPassword = updates.get("newPassword");

			if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
				throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
			}

			if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)[A-Za-z\\d\\W]{6,20}$")) {
				throw new RuntimeException("특수문자, 대문자, 소문자 포함 6~20자리를 입력해주세요.");
			}

			if (passwordEncoder.matches(newPassword, user.getPassword())) {
				throw new RuntimeException("이전에 사용한 비밀번호와 동일합니다.");
			}

			user.setPassword(passwordEncoder.encode(newPassword));
		}

		usersRepository.save(user);
	}

	@Transactional
	public void deleteUser(Long userId) {
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
		usersRepository.delete(user);
	}

	public Users findUserById(Long userId) {
		return usersRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId));
	}

	public Long getLoggedInUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			String email = null;
			if (principal instanceof UserDetails) {
				email = ((UserDetails)principal).getUsername();
			} else if (principal instanceof String) {
				email = principal.toString();
			}
			if (email != null) {
				return usersRepository.findByEmail(email)
					.map(Users::getId)
					.orElseThrow(() -> new IllegalStateException("유효하지 않은 계정입니다."));
			}
		}
		throw new IllegalStateException("해당 유저 없음");
	}

	// public List<String> getDeviceTokens(Long userId) {
	// 	return deviceTokenRepository.findAllByUser_UserId(userId)
	// 		.stream()
	// 		.map(DeviceToken::getToken)
	// 		.collect(Collectors.toList());
	// }
}