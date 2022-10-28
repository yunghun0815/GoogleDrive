package com.kosa.google.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosa.google.dao.DriveDao;
import com.kosa.google.dao.MemberDao;
import com.kosa.google.model.MemberVO;
import com.kosa.google.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService, UserDetailsService{

	@Autowired
	MemberDao memberDao;
	
	@Autowired
	DriveDao driveDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO vo = memberDao.login(username);
		return User.builder()
					.username(vo.getId())
					.password(vo.getPassword())
					.roles(vo.getRole())
					.build();

	}

	@Transactional
	@Override
	public void signup(MemberVO vo) {
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		vo.setRole("USER");
		memberDao.signup(vo);
		driveDao.defaultDirectory(vo.getId());
		driveDao.defaultDrive(vo.getId());
	}

	@Override
	public MemberVO memberInfo(String id) {
		// TODO Auto-generated method stub
		return memberDao.login(id);
	}
	
}
