package kr.co.elasticworks.api.domain;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class Comment {
	private int boardIdx;
	private int commentIdx;
	private String commentWriter;
	private String commentContent;
	private LocalDateTime commentInsertTime;
	private LocalDateTime commentUpdateTime;
	private int commentDeleteYn;
	private LocalDateTime commentDeleteTime;
}
