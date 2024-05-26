package org.zerock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_reviews", indexes = {
	@Index(columnList = "tno")	
} )
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "todoEntity")
@Getter
public class ReviewEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	
	private int score;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tno")
	private TodoEntity todoEntity;
	
//	@ToString(exclude = "todoEntity") 어노테이션을 사용함으로써 
//	ReviewEntity의 toString 메서드에서 todoEntity 필드를 제외하여 성능 문제를 방지하고, 
//	순환 참조를 피하며, 출력 결과를 간결하게 유지할 수 있습니다
}
