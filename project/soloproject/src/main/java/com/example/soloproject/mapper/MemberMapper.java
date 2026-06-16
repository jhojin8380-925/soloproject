package com.example.soloproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.soloproject.dto.BoardDTO;
import com.example.soloproject.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	
//	----------------------------------------
//	===== [회원가입] ===== 
	void insertMember(MemberDTO memberDTO);
	
//	===== [아이디 중복 확인] =====	
	int countByLoginId(@Param("loginId") String loginId);
	
//	===== [로그인] =====
	MemberDTO selectByLoginIdAndPwd(@Param("loginId") String loginId, @Param("pwd") String pwd);
	
//	===== [사용자 게시글 목록 조회] =====
	List<BoardDTO> selectByBoardList(
	        @Param("memberId") int memberId,
	        @Param("offset") int offset,
	        @Param("size") int size);
	
//	===== [게시글 수 조회] =====
	int selectByCount(@Param("memberId") int memberId);
	
//	===== [회원 1명 조회] =====
	int selectByone(@Param("memberId") int memberId);
	
//	===== [회원 정보 수정] =====
	void updateMember(MemberDTO memberDTO);
	
//	===== [회원탈퇴] =====
	int deleteMember(@Param("memberId") int memberId);
	
}


