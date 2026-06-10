package com.example.soloproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.soloproject.dto.MemberDTO;
import com.example.soloproject.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
//	----------------------------------------
//	[회원가입 폼]  GET   /member/join
	@GetMapping("/join")
	public String joinFrom() {
		return "member/join";
	}
	
//	[회원가입 처리] POST  /member/join
	@PostMapping("/join")
	public String join(MemberDTO memberDTO, Model model) {
		if(memberService.isLoginIdDuplicated(memberDTO.getMemberLoginId())) {
			model.addAttribute("error", "duplicate");
			return "member/join";
		}
		
		memberService.join(memberDTO);
		return "redirect:/member/login";
	}
	
//	[아이디 중복 확인]
	
	
	
}
