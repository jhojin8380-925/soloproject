package com.example.soloproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.soloproject.dto.BoardDTO;
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
	
//	===== [로그인] =====
	public MemberDTO login(String loginId, String pwd) {
		return memberMapper.selectByLoginIdAndPwd(loginId, pwd);
	}
	
//	===== [사용자 게시글 목록 조회] =====
	public List<BoardDTO> getMyBoardList(
	        int memberId,
	        int offset,
	        int size) {

	    return memberMapper.selectByBoardList(
	            memberId,
	            offset,
	            size);
	}
	
//	===== [사용자 게시글 전체 갯수 조회] =====
	public int getMyBoardCount(int memberId) {
		return memberMapper.selectByCount(memberId);
	}
	
//	===== [회원정보 수정] =====
	public void updateByMember(MemberDTO memberDTO) {
		memberMapper.updateMember(memberDTO);
	}
	
	
}
