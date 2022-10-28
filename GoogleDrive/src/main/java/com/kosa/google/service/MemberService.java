package com.kosa.google.service;

import com.kosa.google.model.MemberVO;

public interface MemberService {
	public void signup(MemberVO vo); 
	public MemberVO memberInfo(String id);
}
