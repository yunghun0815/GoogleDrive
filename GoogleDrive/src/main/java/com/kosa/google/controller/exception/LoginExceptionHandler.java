package com.kosa.google.controller.exception;


import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginExceptionHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String message; //  비밀번호 || 아이디 || 인증 처리 에러
		if(exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException || exception instanceof InternalAuthenticationServiceException) {
			message = "아이디 또는 비밀번호가 맞지 않습니다.";
			log.info(exception.toString());
		}else {
			message = "알 수 없는 이유로 로그인이 실패하였습니다. 관리자에게 문의해주세요.";
			log.info(exception.toString());
		}
		
		message = URLEncoder.encode(message, "UTF-8");
		setDefaultFailureUrl("/member/exception?error=true&exception="+message);
		
		super.onAuthenticationFailure(request, response, exception);
	}

}