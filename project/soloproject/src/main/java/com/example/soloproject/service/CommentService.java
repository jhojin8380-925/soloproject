package com.example.soloproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.soloproject.dto.CommentDTO;
import com.example.soloproject.mapper.CommentMapper;

@Service
public class CommentService {

	@Autowired
	private CommentMapper commentMapper;

//	[댓글 목록]
	public List<CommentDTO> getCommentsByBoardId(int boardId) {
		return commentMapper.selectByBoardId(boardId);
	}

//	[댓글 등록]
	public void insertComment(CommentDTO commentDTO) {
		commentMapper.insertComment(commentDTO);
	}

//	[댓글 삭제]
	public void deleteComment(int commentId) {
		commentMapper.deleteComment(commentId);
	}
}
