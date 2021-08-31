package kr.co.elasticworks.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.elasticworks.api.domain.Board;
import kr.co.elasticworks.api.domain.SearchPagingUtil;
import kr.co.elasticworks.api.domain.User;
import kr.co.elasticworks.api.service.BoardService;
import kr.co.elasticworks.api.service.UserService;

@RestController
@RequestMapping(value="/api/admin")
public class AdminController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BoardService boardService;
	
	//userList 출력
	@RequestMapping(value="/userList")
	public String userList() {
		System.out.println("userListController 실행");
		return "굿";
	}
	
	//전체 boardList 출력
	@RequestMapping(value="/boardList")
		public Map<String, Object> boardList(@RequestParam int page_no,
				@RequestParam String keyword,
				@RequestParam String type) throws Exception {
			Map<String, Object> result = new HashMap<>();
			SearchPagingUtil search = new SearchPagingUtil();
			
			if (page_no <= 0)
				page_no = 1;
			
			if (keyword == null)
				keyword = "";
			
			if (type == null)
				type = "";

			search.setCurrentPageNo(page_no);
			search.setSearchKeyword(keyword);
			search.setSearchType(type);
			
			int boardTotalCount = boardService.getBoardListCnt(search);

			
			search.setTotalBoardCount(boardTotalCount);

			List<Board> boardList = null;
			
			if (boardTotalCount > 0) {
				boardList = boardService.getBoardList(search);

			}

			result.put("pageInfo", search);
			result.put("boardList", boardList);

			return result;
	}
	
	//noticeBoardList출력
	@RequestMapping(value="/noticeBoardList")
	public String noticeBoardList() {
		System.out.println("noticeBoardListController실행");
		return "굿";
	}
	
	//faqBoardList출력
	@RequestMapping(value="/faqBoardList")
	public String faqBoardList() {
		System.out.println("faqBoardListController실행");
		return "굿";
	}
	
	//qnaBoardList출력
	@RequestMapping(value="/qnaBoardList")
	public String qnaBoardList() {
		System.out.println("qnaBoardListController실행");
		return "굿";
	}
}
