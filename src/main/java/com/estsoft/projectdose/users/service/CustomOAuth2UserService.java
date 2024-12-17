package com.estsoft.projectdose.users.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.repository.UsersRepository;
import com.estsoft.projectdose.users.entity.Role;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UsersRepository usersRepository;

	public CustomOAuth2UserService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		OAuth2User oAuth2User;
		try {
			oAuth2User = super.loadUser(userRequest);
		} catch (Exception e) {
			throw new RuntimeException("카카오 로그인 중 오류 발생", e);
		}

		Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");

		if (kakaoAccount == null) {
			throw new IllegalArgumentException("kakao_account 정보가 누락되었습니다.");
		}

		// kakao_account 안에 profile이라는 key가 포함되어 있음
		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

		if (profile == null) {
			throw new IllegalArgumentException("profile 정보가 누락되었습니다.");
		}

		String email = (String) kakaoAccount.get("email");
		String name = (String) profile.getOrDefault("name", "이름 없음");
		String nickname = (String) profile.getOrDefault("nickname", "닉네임 없음");

		Optional<Users> existingUser = usersRepository.findByEmail(email);
		Users user = existingUser.orElseGet(() -> {
			Users newUser = new Users();
			newUser.setEmail(email);
			newUser.setNickname(nickname);
			newUser.setName(name);
			newUser.setRole(Role.ROLE_USER);
			return usersRepository.save(newUser);
		});

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(user.getRole().getAuthority()));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);


		return new DefaultOAuth2User(
			Collections.singleton(user.getRole().getAuthority()),
			oAuth2User.getAttributes(),
			"id"
		);
	}
}
