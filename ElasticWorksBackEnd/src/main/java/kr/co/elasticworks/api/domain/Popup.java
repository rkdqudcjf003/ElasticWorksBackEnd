package kr.co.elasticworks.api.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Popup {

	private int idx;
	private String title;
	private String content;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int useOrNot;
}
