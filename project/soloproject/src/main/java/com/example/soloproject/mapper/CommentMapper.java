package com.example.soloproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.soloproject.dto.CommentDTO;

@Mapper
public interface CommentMapper {

//	[댓글 목록]
	List<CommentDTO> selectByBoardId(@Param("boardId") int boardId);
	
//	[댓글 등록]
	void insertComment(CommentDTO commentDTO);
	
	
	
}
