package kr.co.elasticworks.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public String userList() throws Exception {
		System.out.println("userListController 실행");
		userService.selectAllUser();
		return "굿";
	}
	
	//(전체 or 카테고리별) and (검색키워드 or 검색유형별) list 출력
	@GetMapping(value = "/boardList")
	public Map<String, Object> boardList(@RequestParam(value = "pageNo", required = false) int pageNo,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "categoryIdx", required = false) int categoryIdx) throws Exception {
		Map<String, Object> result = new HashMap<>();
		SearchPagingUtil search = new SearchPagingUtil();

		if (pageNo <= 0)
			pageNo = 1;

		if (keyword == null)
			keyword = "";

		if (type == null)
			type = "";

		if (categoryIdx <= 0)
			categoryIdx = 0;

		search.setCurrentPageNo(pageNo);
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
		System.out.println("selectOneBoardController실행");
		return boardService.selectOneBoard(boardIdx);
	}

	@PostMapping(value = "/insert")
	public int insertBoard(@RequestBody Board boardVo) throws Exception {
		System.out.println("insertBoardController실행");
		return boardService.insertBoard(boardVo);
	}
	
	@PutMapping(value = "/{idx}")
	public int updateBoard(@PathVariable("idx") int idx, @RequestBody  Board board) throws Exception {
		System.out.println("updateBoardController 실행");
		return boardService.updateBoard(idx, board);
	}

<<<<<<< HEAD
	@PutMapping(value = "/delete/{idx}")
	public int deleteOneBoard(@PathVariable("idx") int idx) throws Exception {
		System.out.println("deleteOneBoardController 실행");
		return boardService.deleteOneBoard(idx);
=======
	@PostMapping(value = "/delete/{boardIdx}")
	public int deleteOneBoard(@PathVariable("boardIdx") int boardIdx, Board board) throws Exception {
		return boardService.deleteOneBoard(boardIdx, board);
>>>>>>> branch 'master' of https://github.com/rkdqudcjf003/ElasticWorksBackEnd.git
	}

	
}
