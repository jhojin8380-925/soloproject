package com.example.soloproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.soloproject.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	
//	----------------------------------------
//	===== [회원가입] ===== 
	void insertMember(MemberDTO memberDTO);
	
//	===== [아이디 중복 확인] =====
	
	int countByLoginId(@Param("loginId") String loginId);
	
	
	
}
