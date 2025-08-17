package com.demo.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MySecurityConfig {
	UserDetailsService userDetailService() {
		InMemoryUserDetailsManager userDetailService = new InMemoryUserDetailsManager();
		UserDetails user = User.withUsername("root")
				.password(passwordEncoder().encode("12345")).authorities("read").build();
		userDetailService.createUser(user);
		return userDetailService();
		
	}
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.httpBasic(Customizer.withDefaults());//url
		http.formLogin(Customizer.withDefaults());//form
		http.authorizeHttpRequests(auth->auth.anyRequest().authenticated());
		//http.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.POST,"/cart").hasRole(null));
		return null;
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
