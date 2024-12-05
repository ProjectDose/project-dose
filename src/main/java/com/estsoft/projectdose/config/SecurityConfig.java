package com.estsoft.projectdose.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return webSecurity -> webSecurity.ignoring()
			.requestMatchers("/static/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**");
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
			.formLogin(custom -> custom.loginPage("/login")
				.loginProcessingUrl("/api/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.permitAll())
			.logout(custom -> custom.logoutSuccessUrl("/login")
				.logoutUrl("/api/logout")
				.deleteCookies("SESSION", "JSESSIONID")
				.invalidateHttpSession(true)
				.permitAll()
			)
			.csrf(AbstractHttpConfigurer::disable)
			.build();
	}
}
