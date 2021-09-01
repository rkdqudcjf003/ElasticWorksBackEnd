package kr.co.elasticworks.api.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {
	private int idx;
	private String title;
	private String writer;
	private String content;
	private int categoryIdx;
	private String categoryName;
	private int view;
	private int likeCnt;
	private int deleteYn;
	private LocalDateTime insertTime;
	private LocalDateTime updateTime;
	private LocalDateTime deleteTime; 
}