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
	public List<BoardDTO> getBoardCommunity(int offset, int size, String keyword){
		return boardMapper.selectAll(offset, size, keyword);
	}
	
//	[전체 게시글 수]
	public int getBoardCount(String keyword) {
		return boardMapper.selectCount(keyword);
	}	
	
}
