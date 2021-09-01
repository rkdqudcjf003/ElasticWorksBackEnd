package kr.co.elasticworks.api.service;

import java.util.List;

import kr.co.elasticworks.api.domain.Board;
import kr.co.elasticworks.api.domain.SearchPagingUtil;


public interface BoardService {
	public List<Board> getBoardList(SearchPagingUtil search) throws Exception;
	
	public int getBoardListCnt(SearchPagingUtil search);
	
	public Board selectOneBoard(int idx);

	public int insertBoard(Board boardVo);

	public int updateBoard(int idx, Board board);
	
	public int deleteBoard(int idx, Board board);

}
