package com.example.soloproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.soloproject.dto.MemberDTO;
import com.example.soloproject.service.MemberService;

import jakarta.servlet.http.HttpSession;

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
//		[아이디 중복 확인] - 이미 존재하는 아이디 인지 확인
		if (memberService.isLoginIdDuplicated(memberDTO.getMemberLoginId())) {
			model.addAttribute("error", "duplicate");
			return "member/join";
		}

//		[비밀번호 확인] - 회원가입 처리중 비밀번호와 비밀번호 확인이 같은지 확인
		if (!memberDTO.getMemberPwd().equals(memberDTO.getMemberPwd2())) {
			model.addAttribute("error", "pwderror");
			return "member/join";
		}

		memberService.join(memberDTO);
		return "redirect:/member/login";
	}

//	[로그인 폼] GET /member/login
	@GetMapping("/login")
	public String loginForm() {
		return "member/login";
	}

//	[로그인 처리] POST /member/login
	@PostMapping("/login")
	public String login(@RequestParam("loginId") String loginId, @RequestParam("pwd") String pwd, HttpSession session,
			Model model) {

		MemberDTO loginMember = memberService.login(loginId, pwd);

		if (loginMember == null) {
			model.addAttribute("error", "fail");
			return "member/login";
		}

		session.setAttribute("loginMember", loginMember);

		return "redirect:/board/community";
	}

	
	@GetMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); return "redirect:/board/community"; 
	}
	 

}
