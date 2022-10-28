package com.kosa.google.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.kosa.google.controller.exception.LoginExceptionHandler;
import com.kosa.google.service.SocailOAuth2UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	LoginExceptionHandler loginExceptionHandler;
	
	@Autowired
	SocailOAuth2UserService userService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()  //authorizeRequest : 요청 URL에 따라 접근 권한을 설정 
		.antMatchers("/", "/member/**").permitAll()  //antMatchers : URL 경로 패턴을 지정, permitAll : 모두 접근 허용
		.antMatchers("/admin/**").hasRole("ADMIN") //hasRole : 권한에 따른 접근 허용
		.anyRequest().authenticated(); //나머지는 권한 있어야 접근 가능
		
		http.formLogin()
		.loginPage("/member/login") //로그인 페이지
		.usernameParameter("id")
		.passwordParameter("password")
		.loginProcessingUrl("/login") //로그인 action
		.defaultSuccessUrl("/")  //로그인 성공시 url
		.failureHandler(loginExceptionHandler);  //로그인 실패시

		http.oauth2Login()
			.userInfoEndpoint()
			.userService(userService);

		http.logout()
		.logoutSuccessUrl("/")  //로그아웃 후 url
		.invalidateHttpSession(true);  //로그아웃 후 세션 제거

		http.csrf().disable(); //csrf disable
		
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
	}
	
}
