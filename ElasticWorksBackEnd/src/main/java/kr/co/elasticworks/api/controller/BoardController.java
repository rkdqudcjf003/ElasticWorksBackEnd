package kr.co.elasticworks.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import kr.co.elasticworks.api.service.BoardService;

@RestController
@RequestMapping(value = "/api/board")
public class BoardController {
	private Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	private BoardService boardService;

	@GetMapping(value = "/list")
	public Map<String, Object> boardList(@RequestParam(value = "page_no", required = false) int page_no,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "category", required = false) int category) throws Exception {
		Map<String, Object> result = new HashMap<>();
		SearchPagingUtil search = new SearchPagingUtil();

		if (page_no <= 0)
			page_no = 1;

		if (keyword == null)
			keyword = "";

		if (type == null)
			type = "";
		
		if (category <= 0)
			category = 0;
		
		search.setCurrentPageNo(page_no);
		search.setSearchKeyword(keyword);
		search.setSearchType(type);
		
		int boardTotalCount = 0;
		
		if (category == 0) {
			boardTotalCount = boardService.getAllBoardListCnt(search);
			System.out.println("전체전체");
		} else if (category > 0){
			search.setCategory(category);
			boardTotalCount = boardService.getCategoryBoardListCnt(search);
			System.out.println("카테고리");
		}
		System.out.println(boardTotalCount);
		search.setTotalBoardCount(boardTotalCount);

		List<Board> boardList = null;

		if (boardTotalCount > 0 && category == 0) {
			boardList = boardService.getAllBoardList(search);
		} else if (boardTotalCount > 0 && category > 0) {
			search.setCategory(category);
			boardList = boardService.getCategoryBoardList(search);
		}

		result.put("pageInfo", search);
		result.put("boardList", boardList);

		return result;
	}

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
