package com.kosa.google.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kosa.google.model.MemberVO;

@Repository
@Mapper
public interface MemberDao {
	public void signup(MemberVO vo);
	public MemberVO login(String id);
	public void oauthSignup(MemberVO vo);
	public int loginCheck(String id);
}
