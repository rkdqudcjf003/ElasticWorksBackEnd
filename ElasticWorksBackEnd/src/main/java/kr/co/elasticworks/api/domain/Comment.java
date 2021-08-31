package kr.co.elasticworks.api.domain;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class  Comment{
	private int boardIdx;
	private int idx;
	private String writer;
	private String content;
	private int deleteYn;
	private LocalDateTime insertTime;
	private LocalDateTime updateTime;
	private LocalDateTime deleteTime;
}
