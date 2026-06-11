package com.example.soloproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.soloproject.dto.BoardDTO;

@Mapper
public interface BoardMapper {
	
//	[게시글 목록]
	List<BoardDTO> selectAll(@Param("offset")int offset,
							 @Param("size") int size,
							 @Param("keyword") String keyword); 
	
//	[전체 게시글 수] 페이징 결과 계산에 사용
	int selectCount(@Param("keyword") String keyword);	
	
// 	[카테고리 별 조회] 카테고리 별로 조회
	List<BoardDTO> selectCategory(@Param("offset")int offset,
			 					  @Param("size") int size,
			 					 @Param("keyword") String keyword,
			 					 @Param("category") String category);	
}





