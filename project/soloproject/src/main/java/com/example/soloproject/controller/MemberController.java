package com.example.soloproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.soloproject.dto.BoardDTO;
import com.example.soloproject.dto.MemberDTO;
import com.example.soloproject.service.BoardService;
import com.example.soloproject.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;

//	----------------------------------------
//	[회원가입 폼]  GET   /member/join  ===============
	@GetMapping("/join")
	public String joinFrom() {
		return "member/join";
	}
 
//	[회원가입 처리] POST  /member/join  ===============
	@PostMapping("/join")
	public String join(MemberDTO memberDTO, Model model) {
//		[아이디 중복 확인] - 이미 존재하는 아이디 인지 확인
		if (memberService.isLoginIdDuplicated(memberDTO.getMemberLoginId())) {
			model.addAttribute("error", "duplicate");
			return "member/join";
		}

//		[비밀번호 확인] - 회원가입 처리중 비밀번호와 비밀번호 확인이 같은지 확인
		if (!memberDTO.getMemberPwd().equals(memberDTO.getMemberPwd2())) {
			model.addAttribute("error", "pwderror");
			return "member/join";
		}
		
//		[회원가입]
		memberService.join(memberDTO);
		return "redirect:/member/login";
	}

//	[로그인 폼] GET /member/login  ===============
	@GetMapping("/login")
	public String loginForm(@CookieValue(
								value = "memberCode",
								defaultValue = "")
							String memberCode,
							Model model) {
		
		model.addAttribute("memberCode", memberCode);
		
		return "member/login";

//		로그인 화면 도착시 쿠키 값이 있는지 확인 후 쿠키값을 반환

	}

//	[로그인 처리] POST /member/login  ===============
	@PostMapping("/login")
	public String login(@RequestParam("loginId") String loginId,
	        			@RequestParam("pwd") String pwd,
	        			@RequestParam(value = "rememberId", required = false) String rememberId,
	        			HttpSession session,
	        			HttpServletResponse response,
	        			Model model) {

		MemberDTO loginMember = memberService.login(loginId, pwd);
//		System.out.println("rememberId : " + rememberId);  //아이디 기억 체크 박스 값이 넘어오는지 확인
		if (loginMember == null) {
		    model.addAttribute("error", "fail");
		    return "member/login";
		}

//		[ 아이디 기억 체크 O 쿠키 생성 ]
		if (rememberId != null) {
		    Cookie cookie = new Cookie("memberCode", loginMember.getMemberLoginId());
		    cookie.setMaxAge(60 * 60 * 24 * 30);
		    cookie.setPath("/");
		    response.addCookie(cookie);

//		    System.out.println("쿠키 생성");  //쿠키 값이 넘어오는지 확인
		} else {
//		[ 아이디 기억 체크 X 쿠키 삭제 ]
		    Cookie cookie = new Cookie("memberCode", "");
		    cookie.setMaxAge(0);
		    cookie.setPath("/");
		    response.addCookie(cookie);
		}
		
//		[로그인 기록 담기]
		session.setAttribute("loginMember", loginMember);

		return "redirect:/board/community";

	}

//	[아이디 기억] 체크박스가 체크 되어있으면 쿠키 생성
	

//	[로그아웃]  ==============================
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/board/community";
	}

//	[사용자 게시글 목록 조회]  ====================
	@GetMapping("/mypage")
	public String mypageBoardList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			HttpSession session, MemberDTO memberDTO) {
		
//		- 게시글을 페이징 처리 (몇개 표현할지)
		int size = 8;
		int offset = (page - 1) * size;	
		
//		변수 생성
		List<BoardDTO> boardList;
		int totalCount;
		
//		세션에 담겨있는(로그인정보) 값을 loginMember 로 가져옴
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		
//		boardList 변수 안에 DB에서 가져온 사용자 본인 게시글 값들을 가져옴
		boardList = memberService.getMyBoardList(
		        loginMember.getMemberId(),
		        offset,
		        size);
	
//		사용자 게시글 총 갯수를 가져옴
		totalCount = memberService.getMyBoardCount(loginMember.getMemberId());
		

//	    board 안에 게시글들을 담아서 각각의 댓글 갯수를 구함
		for (BoardDTO board : boardList) {
		    int count = boardService.getCommentCount(board.getBoardId());
		    board.setCommentCount(count);
		}
		
//		전체 페이지 수를 계산함 ex) 21 / 8 = 2.6 => 3페이지 필요
		int totalPages = (int) Math.ceil((double) totalCount / size);

//		게시글이 1개도 없는 경우 페이지 1로 설정
		if (totalPages == 0) {
			totalPages = 1;
		}
		
//		category 의 값을 한글로 변환
		for (BoardDTO board : boardList) {

			if ("new".equals(board.getBoardCategory())) {
				board.setBoardCategory("가입인사");
			} else if ("free".equals(board.getBoardCategory())) {
				board.setBoardCategory("자유게시판");
			} else if ("question".equals(board.getBoardCategory())) {
				board.setBoardCategory("질문게시판");
			}
		}
		
//		화면으로 보내기
		model.addAttribute("loginMember", loginMember);
		model.addAttribute("boardList", boardList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		return "member/mypage";
		
	}
	
//	[회원정보 수정 폼] GET /member/update   ====================
	@GetMapping("/update/{memberId}")
	public String updateForm(@PathVariable("memberId") int memberId, HttpSession session, Model model) {
		
//		로그인 정보 확인
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/member/login";
		}
//		회원 정보와 로그인 정보가 동일한지 확인
		if (loginMember.getMemberId() != memberId) {
			return "redirect:/member/mypage";
		}
		
		
		model.addAttribute("loginMember", loginMember);
		return "/member/update";
	}
	
//	[회원정보 수정 처리] POST /member/update/{memberId}  ==========
	@PostMapping("/update/{memberId}")
	public String updateMember(@PathVariable("memberId") int memberId, MemberDTO memberDTO, HttpSession session, Model model) {
		
//		로그인정보 확인
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/member/login";
		}
//		회원 정보와 로그인 정보가 동일한지 확인
		if (loginMember.getMemberId() != memberId) {
			return "redirect:/member/mypage";
		}
		
//		비밀번호와 비밀번화확인 폼을 둘다 일치하게 작성하였는지를 확인 
		if (!memberDTO.getMemberPwd().equals(memberDTO.getMemberPwd2())) {
			model.addAttribute("error", "pwderror");
			return "/member/update";
		}
		
//		회원정보 수정
		memberDTO.setMemberId(memberId);
		memberService.updateByMember(memberDTO);
		
		session.setAttribute("loginMember", memberDTO);
		return "redirect:/member/mypage";
		
	}
	
//	[회원탈퇴] POST /member/delete/{memberId}  ===============
	@PostMapping("/delete/{memberId}")
	public String deleteMember(@PathVariable("memberId") int memberId,
							HttpSession session) {
	
//		로그인 정보 확인 
		MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "redirect:/member/login";
		}
//		회원 정보와 로그인 정보가 동일한지 확인
		if (loginMember.getMemberId() != memberId) {
			return "redirect:/member/mypage";
		}
		
		memberService.delMember(memberId);
		session.invalidate();
		return "redirect:/board/community";
	}
	
}
