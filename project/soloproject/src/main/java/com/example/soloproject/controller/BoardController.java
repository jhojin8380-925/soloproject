package com.example.soloproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.soloproject.dto.BoardDTO;
import com.example.soloproject.dto.CommentDTO;
import com.example.soloproject.dto.MemberDTO;
import com.example.soloproject.service.BoardService;
import com.example.soloproject.service.CommentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private CommentService commentService;

//	[게시글 목록] GET /board/community
	@GetMapping("/community")
	public String community(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "category", defaultValue = "all") String category) {

//		한 페이지에 10개 게시글 조회
		int size = 10;
		int offset = (page - 1) * size;

		List<BoardDTO> boardList;
		int totalCount;
		
		// ===== 전체보기 =====
		if ("all".equals(category)) {

			boardList = boardService.getBoardList(offset, size, keyword);

			totalCount = boardService.getBoardCount(keyword);
			
//			==== 댓글 갯수 조회 ====
			for (BoardDTO board : boardList) {
			    int count = boardService.getCommentCount(board.getBoardId());
			    board.setCommentCount(count);
			}

		}
		// ===== 카테고리별 =====
		else {

			boardList = boardService.getCategoryList(offset, size, keyword, category);

			// ⭐ 카테고리 게시글 수 조회
			totalCount = boardService.getCategoryCount(keyword, category);
			
//			==== 댓글 갯수 조회 ====
			for (BoardDTO board : boardList) {
			    int count = boardService.getCommentCount(board.getBoardId());
			    board.setCommentCount(count);
			}
		}

		// ===== 페이지 수 계산 =====
		int totalPages = (int) Math.ceil((double) totalCount / size);

		if (totalPages == 0) {
			totalPages = 1;
		}

		// ===== 카테고리 한글 변환 =====
		for (BoardDTO board : boardList) {

			if ("new".equals(board.getBoardCategory())) {
				board.setBoardCategory("가입인사");
			} else if ("free".equals(board.getBoardCategory())) {
				board.setBoardCategory("자유게시판");
			} else if ("question".equals(board.getBoardCategory())) {
				board.setBoardCategory("질문게시판");
			}
		}

		// ===== 화면으로 전달 =====
		model.addAttribute("boardList", boardList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", category);
		return "board/community";
	}
//	[게시글 상세보기] GET /board/detail/1

	@GetMapping("/detail/{boardId}")
	public String detail(@PathVariable("boardId") int boardId, Model model, HttpSession session) {

//		[게시글 조회수 증가]
		boardService.incrementHit(boardId);
		BoardDTO board = boardService.getBoardId(boardId);
		model.addAttribute("board", board);

//		[댓글 목록]
		List<CommentDTO> commentList = commentService.getCommentsByBoardId(boardId);
		model.addAttribute("commentList", commentList);

//	      로그인 회원 정보 - 수정 , 삭제 버튼 댓글 입력 폼 표시 여부
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		if (loginMember != null) {
			model.addAttribute("loginMemberId", loginMember.getMemberId());
			model.addAttribute("loginMemberLoginId", loginMember.getMemberLoginId());
		}

		return "board/detail";

	}

//	[게시글 작성 폼 이동] GET /board/write (로그인 확인)
	@GetMapping("/write")
	public String writeForm(HttpSession session, Model model, MemberDTO memberDTO) {

		if (session.getAttribute("loginMember") == null) {
			return "redirect:/member/login";
		}

		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		model.addAttribute("memberRating", loginMember.getMemberRating());
		return "board/write";
	}

//	[게시글 작성 처리] POST /board/write
	@PostMapping("/write")
	public String write(BoardDTO boardDTO, HttpSession session, Model model) {

		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

		if (loginMember == null) {
			return "redirect:/member/login";
		}
		
		if ("category".equals(boardDTO.getBoardCategory())) {
	        model.addAttribute("error", "categorySelect");
	        System.out.println(loginMember.getMemberRating());
	        return "board/write";
	    }

		boardDTO.setMemberId(loginMember.getMemberId());
		boardService.insertBoard(boardDTO);

		boardService.updateRating(loginMember.getMemberId());

		loginMember.setMemberRating(1);

		session.setAttribute("loginMember", loginMember);

		return "redirect:/board/community";
	}

//	[수정 폼 이동] GET  /board/update/1
	@GetMapping("/update/{boardId}")
	public String updateForm(@PathVariable("boardId") int boardId, HttpSession session, Model model) {

		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/member/login";
		}

		BoardDTO board = boardService.getBoardId(boardId);

		if (board.getMemberId() != loginMember.getMemberId()) {
			return "redirect:/board/detail" + boardId;
		}

		model.addAttribute("board", board);
		return "board/update";
	}

//	[수정 처리] POST /board/update/{boardId}
	@PostMapping("/update/{boardId}")
	public String update(@PathVariable("boardId") int boardId, BoardDTO boardDTO, HttpSession session) {

		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

		if (loginMember == null) {
			return "redirect:/member/login";
		}

		BoardDTO board = boardService.getBoardId(boardId);
		if (board.getMemberId() != loginMember.getMemberId()) {
			return "redirect:/board/detail" + boardId;
		}

		boardDTO.setBoardId(boardId);
		boardService.updateBoard(boardDTO);
		return "redirect:/board/detail/" + boardId;
	}

//	[삭제 처리] POST /board/delete/{boardId}
	@PostMapping("/delete/{boardId}")
	public String delete(@PathVariable("boardId") int boardId, HttpSession session) {

		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");

		if (loginMember == null) {
			return "redirect:/member/login";
		}

		BoardDTO board = boardService.getBoardId(boardId);
		if (board.getMemberId() != loginMember.getMemberId()) {
			return "redirect:/board/detail" + boardId;
		}

		boardService.deleteBoard(boardId);
		return "redirect:/board/community";
	}

}
