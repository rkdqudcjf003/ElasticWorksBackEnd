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
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping(value = "/read/{idx}")
	public Board selectOneBoard(@PathVariable("idx") int idx) throws Exception {
		return boardService.selectOneBoard(idx);
	}

	@PostMapping(value = "/insert")
	public int insertBoard(@RequestBody Board boardVo) throws Exception {
		return boardService.insertBoard(boardVo);
	}

	@PutMapping(value = "/{idx}")
	public int updateBoard(@RequestBody @PathVariable("idx") int idx, @RequestBody  Board board) throws Exception {
		return boardService.updateBoard(idx, board);
	}

	@PutMapping(value = "/delete/{idx}")
	public int deleteOneBoard(@PathVariable("idx") int idx) throws Exception {
		return boardService.deleteOneBoard(idx);
	}

}
