package com.estsoft.projectdose.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import com.estsoft.projectdose.users.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

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
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/signup", "/api/auth/checkEmailDuplicate", "/api/auth/checkNicknameDuplicate").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(custom -> custom
				.loginPage("/login")
				.loginProcessingUrl("/api/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.successHandler((request, response, authentication) -> {
					response.setContentType("application/json");
					response.getWriter().write("{\"message\":\"로그인 성공\",\"role\":\"" + authentication.getAuthorities() + "\"}");
				})
				.permitAll()
			)
			.logout(custom -> custom
				.logoutSuccessUrl("/login")
				.logoutUrl("/api/logout")
				.deleteCookies("SESSION", "JSESSIONID")
				.invalidateHttpSession(true)
				.permitAll()
			)
			.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
}
