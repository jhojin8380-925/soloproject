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
	public List<BoardDTO> getBoardList(int offset, int size, String keyword) {
		return boardMapper.selectAll(offset, size, keyword);
	}

//	[전체 게시글 수]
	public int getBoardCount(String keyword) {
		return boardMapper.selectCount(keyword);
	}

//	[카테고리 별 조회]
	public List<BoardDTO> getCategoryList(int offset, int size, String keyword, String category) {
		return boardMapper.selectCategory(offset, size, keyword, category);
	}

//	[카테고리 페이징 갯수]
	public int getCategoryCount(String keyword, String category) {
		return boardMapper.selectCategoryCount(keyword, category);
	}
	
//	[게시물 댓글 갯수 조회]
	public int getCommentCount(int boardId) {
		return boardMapper.selectCommentCount(boardId);
	}
	

//	[게시글 상세 조회]
	public BoardDTO getBoardId(int boardId) {
		return boardMapper.selectById(boardId);
	}

//	[게시글 조회수 증가] BoardController 의 /board/detail 에서 호출
	public void incrementHit(int boardId) {
		boardMapper.updateHit(boardId);
	}

//	[게시글 작성]
	public void insertBoard(BoardDTO boardDTO) {
		boardMapper.insertBoard(boardDTO);
	}

//	[게시글 작성시] - 회원 등급 변경
	public void updateRating(int memberId) {
		boardMapper.updateRating(memberId);
	}

//	[게시글 수정] 
	public void updateBoard(BoardDTO boardDTO) {
		boardMapper.updateBoard(boardDTO);
	}

//	[게시글 삭제]
	public void deleteBoard(int boardId) {
		boardMapper.deleteBoard(boardId);
	}

}
