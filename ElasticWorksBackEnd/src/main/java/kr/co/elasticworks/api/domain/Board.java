package kr.co.elasticworks.api.domain;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class Board {
	private int boardIdx;
	private String boardTitle;
	private String boardWriter;
	private String boardContent;
	private int boardCategory;
	private int boardView;
	private int boardLikeCnt;
	private LocalDateTime boardInsertTime;
	private LocalDateTime boardUpdateTime;
	private int boardDeleteYn;
	private LocalDateTime boardDeleteTime;
}