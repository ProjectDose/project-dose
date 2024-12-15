package com.estsoft.projectdose.users.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.repository.UsersRepository;
import com.estsoft.projectdose.users.entity.Role;

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
		OAuth2User oAuth2User = super.loadUser(userRequest);

		// 카카오 사용자 정보 가져오기
		String provider = userRequest.getClientRegistration().getRegistrationId();

		// 카카오 사용자 정보는 Map 형식으로 반환됩니다.
		Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
		Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttribute("properties");

		String email = (String) kakaoAccount.get("email");
		String nickname = (String) properties.get("nickname");

		// 기존 회원 조회
		Optional<Users> existingUser = usersRepository.findByEmail(email);

		Users user;
		if (existingUser.isEmpty()) {
			// 신규 사용자 등록
			user = new Users();
			user.setEmail(email);
			user.setNickname(nickname);
			user.setName(nickname);
			user.setRole(Role.ROLE_USER);
			usersRepository.save(user);
		} else {
			// 기존 사용자 정보 업데이트 (선택 사항)
			user = existingUser.get();
		}

		return oAuth2User;
	}
}
