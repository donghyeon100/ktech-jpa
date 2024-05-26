package org.zerock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_reviews")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class ReviewEntity {
	private Long rno;
	private int score;
	
	@ManyToOne
	private TodoEntity todoEntity;
}
