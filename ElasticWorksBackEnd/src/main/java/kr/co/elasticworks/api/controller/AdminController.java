package kr.co.elasticworks.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	//(전체 or 카테고리별) and (검색키워드 or 검색유형별) list 출력
	@GetMapping(value = "/boardList")
	public Map<String, Object> boardList(@RequestParam(value = "page_no", required = false) int page_no,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "category", required = false) int categoryIdx) throws Exception {
		Map<String, Object> result = new HashMap<>();
		SearchPagingUtil search = new SearchPagingUtil();

		if (page_no <= 0)
			page_no = 1;

		if (keyword == null)
			keyword = "";

		if (type == null)
			type = "";

		if (categoryIdx <= 0)
			categoryIdx = 0;

		search.setCurrentPageNo(page_no);
		search.setSearchKeyword(keyword);
		search.setSearchType(type);
		search.setCategory(categoryIdx);

		int boardTotalCount = 0;

		boardTotalCount = boardService.getBoardListCnt(search);

		search.setTotalBoardCount(boardTotalCount);

		List<Board> boardList = null;
		
		boardList = boardService.getBoardList(search);

		result.put("pageInfo", search);
		result.put("boardList", boardList);
		
		return result;
	}
	
	//게시판 조회
	@GetMapping(value = "/read/{boardIdx}")
	public Board selectOneBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		return boardService.selectOneBoard(boardIdx);
	}

	@PostMapping(value = "/insert")
	public int insertBoard(@RequestBody Board boardVo) throws Exception {
		return boardService.insertBoard(boardVo);
	}

	@PostMapping(value = "/{boardIdx}")
	public int updateBoard(@PathVariable("boardIdx") int boardIdx, Board boardVo) throws Exception {
		return boardService.updateBoard(boardIdx, boardVo);
	}

	@PostMapping(value = "/delete/{boardIdx}")
	public int deleteBoard(@PathVariable("boardIdx") int boardIdx, Board boardVo) throws Exception {
		return boardService.deleteBoard(boardIdx, boardVo);
	}
	
}
