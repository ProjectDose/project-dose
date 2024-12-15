package com.estsoft.projectdose.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import com.estsoft.projectdose.users.service.CustomUserDetailsService;
import com.estsoft.projectdose.users.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;
	private final CustomOAuth2UserService customOAuth2UserService;

	public SecurityConfig(CustomUserDetailsService userDetailsService, CustomOAuth2UserService customOAuth2UserService) {
		this.userDetailsService = userDetailsService;
		this.customOAuth2UserService = customOAuth2UserService;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/home", "/auth/login", "/oauth2/authorization/kakao",
					"/api/auth/signup", "/api/auth/checkEmailDuplicate", "/api/auth/checkNicknameDuplicate",
					"/api/login", "/welcome").permitAll() // 로그인 페이지, 로그인 요청, 회원가입 페이지 접근 허용
				.anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
			)
			.formLogin(custom -> custom
				.loginPage("/login") // 로그인 페이지 경로 명확히 설정 (Spring Security의 기본 경로 사용)
				.loginProcessingUrl("/api/login") // 로그인 요청 경로 (POST 요청)
				.defaultSuccessUrl("/home", true) // 로그인 성공 후 /home으로 이동
				.permitAll() // 로그인 페이지는 누구나 접근 가능
			)
			.logout(custom -> custom
				.logoutSuccessUrl("/") // 로그아웃 후 메인 페이지로 리디렉션
				.logoutUrl("/api/logout")
				.deleteCookies("SESSION", "JSESSIONID")
				.invalidateHttpSession(true)
				.permitAll()
			)
			// .oauth2Login(oauth -> oauth
			// 	.loginPage("/login") // 카카오 로그인 버튼이 있는 페이지
			// 	.defaultSuccessUrl("/home", true) // 로그인 성공 후 /home으로 리디렉션
			// 	.failureUrl("/login?error=true") // 로그인 실패시 이동 경로
			// 	.userInfoEndpoint(userInfo ->
			// 		userInfo.userService(customOAuth2UserService)
			// 	)
			// )
			.csrf(AbstractHttpConfigurer::disable); // CSRF 비활성화 (필요에 따라 설정)

		http.authenticationManager(authenticationManager(http));

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authManagerBuilder
			.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder());
		return authManagerBuilder.build();
	}
}
