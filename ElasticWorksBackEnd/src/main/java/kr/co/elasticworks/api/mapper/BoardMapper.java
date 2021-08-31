package kr.co.elasticworks.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.elasticworks.api.domain.Board;
import kr.co.elasticworks.api.domain.SearchPagingUtil;

@Mapper
public interface BoardMapper {
	public List<Board> getAllBoardList(SearchPagingUtil search);

	public int getAllBoardListCnt(SearchPagingUtil search);
	
	public List<Board> getCategoryBoardList(SearchPagingUtil search);

	public int getCategoryBoardListCnt(SearchPagingUtil search);

	public Board selectOneBoard(int boardIdx);

	public int insertBoard(Board boardVo);

	public int updateBoard(Board board);

	public Board findByIdx(int boardIdx);
	
	public int deleteBoard(Board boardVo);

}
