package org.zerock.dto;

import lombok.Data;

// Todo 목록 조회 전용 DTO
// tno, title, 리뷰 수

@Data
public class TodoListDTO {

	private Long tno;
	private String title;
	private long reviewCount;
}
