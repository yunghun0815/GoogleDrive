package com.kosa.google.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class MemberVO {
	private String id; //아이디
	private String password; //비밀번호
	private String name; //이름
	private String email; ///이메일
	private int birthYear; //태어난 년
	private int birthMonth; //태어난 월
	private int birthDay; // 태어난 일
	private String gender; //성별
	private String provider; //소셜 로그인 확인
	private String role; //권한

}
