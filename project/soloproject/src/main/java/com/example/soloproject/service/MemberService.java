package com.example.soloproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.soloproject.dto.MemberDTO;
import com.example.soloproject.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
//	----------------------------------------	
//	===== [회원가입] =====
	public void join(MemberDTO memberDTO) {
		memberMapper.insertMember(memberDTO);
	}
	
//	===== [아이디 중복 확인] =====
	public boolean isLoginIdDuplicated(String loginId) {
		return memberMapper.countByLoginId(loginId) > 0;
	}
	
}
