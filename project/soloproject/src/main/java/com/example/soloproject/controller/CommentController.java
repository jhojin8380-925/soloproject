package com.example.soloproject.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.soloproject.dto.CommentDTO;
import com.example.soloproject.dto.MemberDTO;
import com.example.soloproject.service.CommentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

//	[댓글 등록] POST /comment/insert

	@PostMapping("/insert")
	@ResponseBody
	public Map<String, Object> insert(@RequestBody CommentDTO commentDTO, HttpSession session) {

		Map<String, Object> result = new HashMap<>();

		
//		로그인 상태 여부 확인
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		if (loginMember == null) {
//	         로그인 안된 상태 - 실패 응답을 반환
//	         {"success":false, "message":"로그인이 필요합니다"}
			result.put("success", false);
			result.put("message", "로그인이 필요합니다");
			return result;
		}
		
//		회원 등급이 0이라면 댓글 작성 불가
		if(loginMember.getMemberRating() == 0) {
			result.put("success", false);
			result.put("message", "아직 이용 불가능한 시스템입니다.");
			return result;
		}
		
//		comment 객체 안으 memberId 값을 현제 로그인한 memberId 값으로 설정
		commentDTO.setMemberId(loginMember.getMemberId());

//		댓글 등록
		commentService.insertComment(commentDTO);

//		로그인 아디 설정 
		commentDTO.setMemberLoginId(loginMember.getMemberLoginId());

//		작성 시간 설정
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		commentDTO.setCommentDate(now);

//		화면 전송값 
		result.put("success", true);
		result.put("comment", commentDTO);
		return result;
	}

//	[댓글 삭제]
	@PostMapping("/delete/{commentId}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("commentId") int commentId, HttpSession session) {
		Map<String, Object> result = new HashMap<>();

		// 로그인 여부 확인
		if (session.getAttribute("loginMember") == null) {
			result.put("success", false);
			result.put("message", "로그인이 필요합니다.");
			return result;
		}

		// DB에서 댓글 삭제
		commentService.deleteComment(commentId);

//	      성공 응답 반환
//	      {"success" : true}
		result.put("success", true);
		return result;
	}
}
