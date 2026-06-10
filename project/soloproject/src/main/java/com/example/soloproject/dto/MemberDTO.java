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
	private String memberPhone;
	private int memberRating;
	
}
