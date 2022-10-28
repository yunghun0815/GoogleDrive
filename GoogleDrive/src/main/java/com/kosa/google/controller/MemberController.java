package com.kosa.google.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosa.google.model.MemberVO;
import com.kosa.google.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberServiceImpl;
	
	@GetMapping("/member/login")
	public String loginPage() {
		return "/member/login";
	}
	
	@GetMapping("/member/exception")
	public String loginError(@RequestParam(value="error", required = false)String error, 
							 @RequestParam(value="exception", required = false)String exception, Model model){
		System.out.println(error);
		System.out.println(exception);
		model.addAttribute("error", error);
		model.addAttribute("message", exception);
		return "/member/login";
	}
	
	@GetMapping("/member/signup")
	public String signupPage() {
		return "/member/signup";
	}
	
	@PostMapping("/member/signup")
	public String signup(MemberVO vo, Model model) {
		memberServiceImpl.signup(vo);
		return "/member/login";
	}
}
