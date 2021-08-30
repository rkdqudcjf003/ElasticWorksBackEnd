package kr.co.elasticworks.api.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.elasticworks.api.domain.Board;
import kr.co.elasticworks.api.domain.SearchPagingUtil;
import kr.co.elasticworks.api.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	private Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	BoardMapper boardMapper;

	@Override
	public List<Board> getBoardList(SearchPagingUtil search) throws Exception {
		return boardMapper.getBoardList(search);
	}

	@Override
	public int getBoardListCnt(SearchPagingUtil search) {
		return boardMapper.getBoardListCnt(search);
	}

	@Override
	public Board selectOneBoard(int boardIdx) {
		return boardMapper.selectOneBoard(boardIdx);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public int insertBoard(Board boardVo) {
		return boardMapper.insertBoard(boardVo);
	}

	@Override
	public int updateBoard(int boardIdx, Board boardVo) {
		if (boardIdx <= 0) {
			log.info("잘못된 접근 혹은 존재하지 않는 게시글입니다.");
			return 0;
		} else {
			try {
				Board board = boardMapper.findByIdx(boardIdx);
				
				log.info("Update Board - 수정전 데이터: " + board);
				board.setBoardTitle(boardVo.getBoardTitle());
				board.setBoardWriter(boardVo.getBoardWriter());
				board.setBoardContent(boardVo.getBoardContent());
				board.setBoardCategory(boardVo.getBoardCategory());
				log.info("Update Board - 수정후 데이터: " + board);
				
				int boardUpdateflag = boardMapper.updateBoard(board);
				if (boardUpdateflag > 0) {
					return 1;
				}
			} catch (DataAccessException e) {
				log.info("DataBase 처리 과정 문제 발생.");

			} catch (Exception e) {
				log.info("시스템 문제 발생.");
			}

			log.info("게시글이 수정되지 않았습니다.");
			return 0;
		}
	}

	@Override
	public int deleteBoard(int boardIdx, Board boardVo) {
		if (boardIdx <= 0) {
			log.info("잘못된 접근 혹은 존재하지 않는 게시글입니다.");
			return 0;
		} else {
			try {
				Board board = boardMapper.findByIdx(boardIdx);
				board.setBoardDeleteYn(1);
				
				int boardDeleteflag = boardMapper.deleteBoard(board);

				if (boardDeleteflag > 0) {
					log.info("게시글이 삭제되었습니다.");
					return 1;
				}
			} catch (DataAccessException e) {
				log.info("DataBase 처리 과정 문제 발생.");

			} catch (Exception e) {
				log.info("시스템 문제 발생.");
			}

			log.info("게시글이 삭제되지 않았습니다.");
			return 0;
		}
	}

}
