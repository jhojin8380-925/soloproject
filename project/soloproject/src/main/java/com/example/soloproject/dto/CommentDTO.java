package com.example.soloproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
	
//	MySQL 에서 댓글 데이터 받아올 변수 생성 
	private int commentId;
	private String commentContent;
	private String commentDate;
	private int boardId;
	private int memberId;
	
	private String memberLoginId;
	
	
}
