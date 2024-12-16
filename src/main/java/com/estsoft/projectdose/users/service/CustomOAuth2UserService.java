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

		String provider = userRequest.getClientRegistration().getRegistrationId();

		Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
		Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttribute("properties");

		String email = (String) kakaoAccount.get("email");
		String nickname = (String) properties.get("nickname");

		Optional<Users> existingUser = usersRepository.findByEmail(email);

		Users user;
		if (existingUser.isEmpty()) {
			user = new Users();
			user.setEmail(email);
			user.setNickname(nickname);
			user.setName(nickname);
			user.setRole(Role.ROLE_USER);
			usersRepository.save(user);
		} else {
			user = existingUser.get();
		}

		return oAuth2User;
	}
}
