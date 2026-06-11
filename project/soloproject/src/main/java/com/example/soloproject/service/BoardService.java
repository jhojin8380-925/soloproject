package com.example.soloproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.soloproject.dto.BoardDTO;
import com.example.soloproject.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
//	[게시글 목록]
	public List<BoardDTO> getBoardList(int offset, int size, String keyword){
		return boardMapper.selectAll(offset, size, keyword);
	}
	
//	[전체 게시글 수]
	public int getBoardCount(String keyword) {
		return boardMapper.selectCount(keyword);
	}	
	
//	[카테고리 별 조회]
	public List<BoardDTO> getCategoryList(int offset, int size, String keyword, String category){
		return boardMapper.selectCategory(offset, size, keyword, category);
	}
	
}
