package kr.co.elasticworks.api.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.elasticworks.api.domain.Board;
import kr.co.elasticworks.api.domain.Popup;
import kr.co.elasticworks.api.domain.SearchPagingUtil;
import kr.co.elasticworks.api.service.BoardService;
import kr.co.elasticworks.api.service.PopupService;
import kr.co.elasticworks.api.service.UserService;

@RestController
@RequestMapping(value="/api/admin")
public class AdminController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BoardService boardService;
	
	@Qualifier("popup")
	@Autowired
	PopupService popupService;
	
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
		System.out.println("boardListController실행");
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
		search.setCategoryIdx(categoryIdx);

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
	@GetMapping(value = "/read/{idx}")
	public Board selectOneBoard(@PathVariable("idx") int boardIdx) throws Exception {
		System.out.println("selectOneBoardController실행");
		return boardService.selectOneBoard(boardIdx);
	}

	//게시판 생성
	@PostMapping(value = "/insert")
	public int insertBoard(@RequestBody Board boardVo) throws Exception {
		System.out.println("insertBoardController실행");
		return boardService.insertBoard(boardVo);
	}
	
	//게시판 수정
	@PutMapping(value = "/{idx}")
	public int updateBoard(@PathVariable("idx") int idx, @RequestBody  Board board) throws Exception {
		System.out.println("updateBoardController 실행");
		return boardService.updateBoard(idx, board);
	}
	
	//게시판 삭제
	@PutMapping(value = "/delete/{idx}")
	public int deleteOneBoard(@PathVariable("idx") int boardIdx, Board board) throws Exception {
		return boardService.deleteOneBoard(boardIdx, board);
	}
	
	//전체 팝업 리스트
	@GetMapping(value="/popupList")
	public Map<String, Object> popupList() throws Exception {
		System.out.println("popupListController 실행");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Popup> popupList = popupService.popupAllList();
		map.put("popupList", popupList);
		return map;
	}
	
	@PutMapping(value="/selectOnePopup/{idx}")
	public Popup selectOnePopup(@PathVariable("idx") int popupIdx) throws Exception{
		System.out.println("selectOnePopupController 실행");
		System.out.println("idx:"+popupIdx);
		return popupService.selectOnePopup(popupIdx);
	};
	
	@PostMapping(value="/createPopup")
	public int createPopup(Popup popup) throws Exception{
		System.out.println("createPopupController 실행");
		System.out.println(popup);
		return popupService.createPopup(popup);
	};
	
	@PutMapping(value="/updatePopup/{idx}")
	public int updatePopup(@PathVariable("idx") int popupIdx, @RequestBody Popup popup) throws Exception{
		popup.setIdx(popupIdx);
		System.out.println(popup);
		return popupService.updatePopup(popup);
	};
	
	@PutMapping(value="/deletePopup/{idx}")
	public int deletePopup(@PathVariable("idx") int popupIdx) throws Exception{
		System.out.println("deletePopupController 실행");
		System.out.println(popupIdx);
		return popupService.deletePopup(popupIdx);
	};
	
}
