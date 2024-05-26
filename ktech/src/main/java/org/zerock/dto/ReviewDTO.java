package org.zerock.dto;

import org.zerock.entity.ReviewEntity;
import org.zerock.entity.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
	private Long rno;
	private int score;
	private Long tno;
	
	public ReviewDTO(ReviewEntity entity) {
		this.rno = entity.getRno();
		this.score = entity.getScore();
		this.tno = entity.getTodoEntity().getTno();
	}
	
	public ReviewEntity toEntity() {
		
		// PK만 존재하는 가짜 TodoEntity
		TodoEntity todoEntity = TodoEntity.builder().tno(tno).build();
		
		ReviewEntity entity = ReviewEntity.builder()
				.rno(this.rno)
				.score(this.score)
				.todoEntity(todoEntity)
				.build();
		return entity;
	}
}
