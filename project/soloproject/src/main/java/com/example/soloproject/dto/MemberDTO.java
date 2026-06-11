package com.example.soloproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {
	
//	MySQL 에서 회원 데이터 받아올 변수 생성 
	private int memberId;
	private String memberLoginId;
	private String memberName;
	private String memberEmail;
	private String memberPwd;
//	DB에는 없지만 비밀번호 재확인을 위해 변수 생성
	private String memberPwd2;
	private String memberPhone;
	private int memberRating;
	
}
