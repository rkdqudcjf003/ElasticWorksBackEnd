package kr.co.elasticworks.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.elasticworks.api.domain.Board;
import kr.co.elasticworks.api.domain.SearchPagingUtil;

@Mapper
public interface BoardMapper {
	public List<Board> getBoardList(SearchPagingUtil search);

	public int getBoardListCnt(SearchPagingUtil search);

	public Board selectOneBoard(int idx);

	public int insertBoard(Board boardVo);

	public int updateBoard(Board board);

	public Board findByIdx(int idx);
	
	public int deleteOneBoard(Board board);

}
