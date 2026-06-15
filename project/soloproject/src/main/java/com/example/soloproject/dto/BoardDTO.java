package com.example.soloproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {
	
//	MySQL 에서 게시글 데이터 받아올 변수 생성 	
	private int boardId;
	private String boardTitle;
	private String boardContent;
	private String boardCategory;
	private int boardHit;
	private String boardDate;
	private int memberId;
	
//	댓글 갯수 저장할 값
	private int commentCount;
	
//	JOIN 이용 하여 가져올 데이터 값
	private String memberLoginId;
	
	
	
}
