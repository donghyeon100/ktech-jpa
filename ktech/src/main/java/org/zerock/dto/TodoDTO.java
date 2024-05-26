package org.zerock.dto;

import java.time.LocalDate;

import org.zerock.entity.TodoEntity;

import lombok.Data;

@Data
public class TodoDTO {
	private Long tno;
	private String title;
	private String writer;
	private LocalDate dueDate;
	
	/** Entity -> DTO
	 * @param entity
	 */
	public TodoDTO(TodoEntity entity) {
		this.tno = entity.getTno();
		this.title = entity.getTitle();
		this.writer = entity.getWriter();
		this.dueDate = entity.getDueDate();
		
	}
	
	/** DTO -> Entity
	 * @return
	 */
	public TodoEntity toEntity() {
		TodoEntity entity = TodoEntity.builder()
				.tno(this.tno)
				.title(this.title)
				.writer(this.writer)
				.dueDate(this.dueDate)
				.build();
		return entity;
	}
}
