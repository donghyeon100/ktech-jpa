package org.zerock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Entity : DB의 테이블과 매핑될 Java Class

@Entity // 클래스가 JPA 엔티티임을 나타냄. (데이터베이스의 테이블과 매핑됨)
@Table(name = "tbl_todos") // 엔티티 클래스와 매핑될 데이터베이스 테이블을 지정
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class TodoEntity {

	// JPA가 데이터 비교시 equals(), hashCode()를 사용하기 때문에
	// 기본 자료형보다 Wrapper Class를 사용하길 권장
	
	@Id // 엔티티의 기본 키(primary key) 매핑 필드
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키의 생성을 JPA가 관리 , 기본 키의 생성을 JPA가 관리
	//  GenerationType.IDENTITY : 데이터베이스에 의해 기본 키 값이 자동으로 증가되는 방식
	private Long tno;
	
	private String title;
	
	private String writer;
	
	private java.time.LocalDate dueDate;

	public void addTitle(String titl) {
		this.title = titl;
	}
}
