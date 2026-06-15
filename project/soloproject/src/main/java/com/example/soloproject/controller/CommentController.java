package com.example.soloproject.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		if (loginMember == null) {
//	         로그인 안된 상태 - 실패 응답을 반환
//	         {"success":false, "message":"로그인이 필요합니다"}
			result.put("success", false);
			result.put("message", "로그인이 필요합니다");
			return result;
		}
		
		
		commentDTO.setMemberId(loginMember.getMemberId());
		
		commentService.insertComment(commentDTO);
		
		commentDTO.setMemberLoginId(loginMember.getMemberLoginId());
		
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		commentDTO.setCommentDate(now);
		
		result.put("success", true);
		result.put("comment", commentDTO);
		return result;
	}

}
