package org.zerock.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.dto.TodoDTO;
import org.zerock.dto.TodoListDTO;
import org.zerock.entity.QReviewEntity;
import org.zerock.entity.QTodoEntity;
import org.zerock.entity.TodoEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

// QuerydslRepositorySupport : 페이징, 검색을 간단히 처리할 수 있음

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

	
	public TodoSearchImpl() {
		// 도메인 클래스를 부모(QuerydslRepositorySupport) 생성자 파라미터로 전달
		super(TodoEntity.class);
	}
	
	
	//Querydsl을 사용하여 TodoEntity에 대한 페이지네이션된 쿼리를 생성
	@Override
	public Page<TodoDTO> listSearch(Pageable pageable) {
		
		// QTodoEntity를 통해 타입 안전한 쿼리를 작성
		QTodoEntity todoEntity = QTodoEntity.todoEntity;
		
		
		// JPQLQuery는 Querydsl 라이브러리에서 제공하는 인터페이스로, 
		// JPQL(Java Persistence Query Language) 쿼리를 작성하고 실행하기 위한 기능을 제공
		
		// 메서드를 이용해서 SQL을 생성
		JPQLQuery<TodoEntity> query = from(todoEntity);
		
		/* ----- 동적 SQL 적용 부분!!!! ----- */
		query.where(todoEntity.tno.gt(0)); // == where tno > 0  (인덱스 사용을 위해)
		this.getQuerydsl().applyPagination(pageable, query); 
		
		
		// JPQLQuery이 만든 SQL 로그로 출력
		log.info(query);
		//from TodoEntity todoEntity
		//where todoEntity.tno > ?1
		//order by todoEntity.tno desc
		
		// todoEntity의 select 결과를 TodoDTO 형태로 변경(Projections, 전파)
		// (이 때, TodoDTO의 생성자를 사용)
		JPQLQuery<TodoDTO> dtoQuery = query.select(Projections.constructor(TodoDTO.class, todoEntity));
		
		
		List<TodoDTO> list = dtoQuery.fetch(); // query 수행 결과를 List 형태로 받아옴
		long count = dtoQuery.fetchCount(); // 조회된 행의 개수를 long형으로 반환
		
		// 조회 결과, 페이지 정보, 조회된 행의 개수가 담긴 PageImpl (Page 상속 객체)를 반환
		return new PageImpl<>(list, pageable, count);
	}
	
	
	
	// Todo 목록 조회 + 리뷰 수 조회
	@Override
	public Page<TodoListDTO> listSearchCount(Pageable pageable) {
		
		// QTodoEntity를 통해 타입 안전한 쿼리를 작성
		QTodoEntity todoEntity = QTodoEntity.todoEntity;
		
		QReviewEntity reviewEntity = QReviewEntity.reviewEntity;
				
		
		// 쿼리 작성을 위해 JPQLQuery 객체 생성
		JPQLQuery<TodoEntity> query = from(todoEntity)
				.leftJoin(reviewEntity) 
				.on(reviewEntity.todoEntity.eq(todoEntity)) // ANSI LEFT JOIN을 JAVA로 쓴 것
				.groupBy(todoEntity);
		
		
//		query.where(todoEntity.tno.gt(0)); // == where tno > 0  (인덱스 사용을 위해)
		
		this.getQuerydsl().applyPagination(pageable, query);
		
		//query.select(Projections.bean(쿼리 결과를 저장할 클래스, 속성))
		JPQLQuery<TodoListDTO> dtoQuery = query.select(Projections.bean(TodoListDTO.class,
				todoEntity.tno, todoEntity.title,
				// 조회 결과와 TodoListDTO의 필드명이 같으면 자동 세팅
				
				reviewEntity.rno.countDistinct().as("reviewCount")
				// 조회된 reviewEntity의 rno 개수 카운트(중복 제거) 
				//	-> reviewCount로 별칭 줘서 TodoListDTO.reviewCount에 자동 세팅
				));
		
		List<TodoListDTO> dtoList = dtoQuery.fetch();
		long count = dtoQuery.fetchCount();
		
		return new PageImpl<>(dtoList, pageable, count);
	}
}







