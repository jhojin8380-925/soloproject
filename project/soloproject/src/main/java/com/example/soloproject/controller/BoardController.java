package com.example.soloproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.soloproject.dto.BoardDTO;
import com.example.soloproject.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

//	[게시글 목록] GET /board/community

	@GetMapping("/community")
	public String community(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "category", defaultValue = "all") String category) {

		int size = 10;
		int offset = (page - 1) * size;

//		[카테고리] all (전체보기) <= 기본 페이지
		if (category.equals("all")) {
			List<BoardDTO> boardList = boardService.getBoardList(offset, size, keyword);
			int totalCount = boardService.getBoardCount(keyword); // 전체 게시글 수
//	    페이지 수 계산
			int totalPages = (int) Math.ceil((double) totalCount / size);
			if (totalPages == 0) {
				totalPages = 1;
			}

			model.addAttribute("boardList", boardList);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("keyword", keyword);
			model.addAttribute("category", category);

			return "board/community";
		} else { 
//			[카테고리] 가입인사, 자유게시판, 질문게시판 
			List<BoardDTO> boardList = boardService.getCategoryList(offset, size, keyword, category);
			int totalCount = boardService.getBoardCount(keyword);
			int totalPages = (int) Math.ceil((double) totalCount / size);
			if(totalPages == 0) {
				totalPages = 1;
			}
			
			model.addAttribute("boardList", boardList);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("keyword", keyword);
			model.addAttribute("category", category);
			
			return "board/community";
		}
		
	}

}
