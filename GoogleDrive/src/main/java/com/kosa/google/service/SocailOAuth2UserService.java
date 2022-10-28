package com.kosa.google.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosa.google.dao.DriveDao;
import com.kosa.google.dao.MemberDao;
import com.kosa.google.model.MemberVO;

@Service
public class SocailOAuth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	MemberDao memberDao;
	@Autowired
	DriveDao driveDao;
	
	@Transactional
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest); //oauth 로그인 response 값
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId(); //oauth 서비스 이름naver, google 등
		
		Map<String, Object> attributes = oauth2User.getAttributes(); // oauth 유저 정보
		
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // OAuth 로그인 시 키(pk)가 되는 값
		String email = null;
		String name = null;
		if(registrationId.equals("naver")) {
			Map<String, String> info = (Map<String,String>)oauth2User.getAttribute("response");
			email = info.get("email");
			name = info.get("name");
		}else {
			email = oauth2User.getAttribute("email");
			name = oauth2User.getAttribute("name");
		}
		
		MemberVO vo = MemberVO.builder()
								.id(email)
							   .name(name)
							   .email(email)
							   .password("social"+ System.nanoTime())
							 //  .password(passwordEncoder.encode("social" + System.nanoTime()))
							   .provider(registrationId)
							   .role("USER")
							   .build();
		//처음 로그인 사용자 가입처리
		if(memberDao.loginCheck(email) == 0) {
			memberDao.oauthSignup(vo);
			driveDao.defaultDirectory(vo.getId());
			driveDao.defaultDrive(vo.getId());
		}
		
		 return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, userNameAttributeName);
	}
}