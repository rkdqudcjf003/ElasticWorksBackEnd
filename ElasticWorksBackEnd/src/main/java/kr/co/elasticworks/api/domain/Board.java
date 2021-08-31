package kr.co.elasticworks.api.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {
	private int idx;
	private String title;
	private String writer;
	private String content;
	private int category;
	private int view;
	private int likeCnt;
	private LocalDateTime insertTime;
	private LocalDateTime updateTime;
	private int deleteYn;
	private LocalDateTime deleteTime;
}