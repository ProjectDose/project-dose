package com.estsoft.projectdose.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import com.estsoft.projectdose.users.service.CustomUserDetailsService;
import com.estsoft.projectdose.users.service.CustomOAuth2UserService;

@Configuration
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
				.requestMatchers(
					"/css/**", "/js/**", "/images/**", "/static/**",
					"/auth/**", "/oauth2/**", "/api/auth/**", "/error", "/favicon.ico",
					"/api/ai/proxy", "/api/v1/**", "/api/v1/fcm/**","/firebase","/ai", "/firebase-messaging-sw.js"
				).permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/auth/login")
				.loginProcessingUrl("/auth/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/", true)
				.failureHandler((request, response, exception) -> {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.setContentType("application/json");
					response.getWriter().write("{\"error\": \"로그인 실패\"}");
				})
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies("SESSION", "JSESSIONID")
				.permitAll()
			)
			.oauth2Login(oauth -> oauth
				.loginPage("/auth/login")
				.defaultSuccessUrl("/", true)
				.failureUrl("/auth/login?error=true")
				.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
			)

			.csrf(csrf -> csrf.disable())
			.rememberMe(rememberMe -> rememberMe
				.key("uniqueAndSecret")
				.tokenValiditySeconds(86400)
			);

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
