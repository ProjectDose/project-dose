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

	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/login", "/api/auth/signup", "/api/auth/checkEmailDuplicate", "/api/auth/checkEmailDuplicate", "/error").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(custom -> custom
				.loginPage("/auth/login")
				.loginProcessingUrl("/api/auth/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.successHandler((request, response, authentication) -> {
					response.setContentType("application/json");
					response.getWriter().write("{\"message\":\"로그인 성공\",\"role\":\"" + authentication.getAuthorities() + "\"}");
				})
				.permitAll()
			)
			.logout(custom -> custom
				.logoutSuccessUrl("/")
				.logoutUrl("/api/auth/logout")
				.deleteCookies("SESSION", "JSESSIONID")
				.invalidateHttpSession(true)
				.permitAll()
			)
			.oauth2Login(oauth -> oauth
				.loginPage("/login")
				.defaultSuccessUrl("/home", true)
				.failureUrl("/login?error=true")
				.userInfoEndpoint(userInfo ->
					userInfo.userService(customOAuth2UserService)
				)
			)
			.csrf(AbstractHttpConfigurer::disable);

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
