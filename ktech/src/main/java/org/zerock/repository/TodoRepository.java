package org.zerock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.dto.TodoDTO;
import org.zerock.entity.TodoEntity;
import org.zerock.repository.search.TodoSearch;

// JpaRepository<TodoEntity, Long> : 간단한 CRUD 제공
// TodoSearch : 리스트 조회, 검색을 사용자가 정의하여 제공
public interface TodoRepository extends JpaRepository<TodoEntity, Long>, TodoSearch {

	// @Query : Spring Data JPA에서 
	// 직접 JPQL(Java Persistence Query Language) SQL 쿼리를 정의할 수 있게 해줌
	
	// - 테이블명(Entity) 대소문자 구분 필수!
	// - *(모든 컬럼) 사용 불가 -> 대신 테이블(Entity) 별칭 작성
	
	
	@Query("select t from TodoEntity t ")
	Page<TodoDTO> list1(Pageable pageable);
	// -> return type을 DTO로 지정하고
	//   DTO에 Entity를 parameter하는 생성자를 만들면
	//   조회 결과가 Entity 값이 DTO에 담기게 된다!!
	
	
//  @Query("select t from TodoEntity t ")
//	Page<TodoEntity> list1(Pageable pageable);
	// -> findAll(pageable)과 같은 결과를 반환하는 메서드인데 왜 따로 메서드 만들어??
	
	// 1) 원하는 컬럼만 조회 가능 (t.title)
	// 2) JOIN 가능
	// 3) DB와 연결된 Entity를 반환해서 사용하면 위험 -> DTO로 옮기기
	
	// -------------------------------------------------------------------------
	
	
	/** 조회된 내용을 DTO로 변환하는 메서드
	 * @param tno
	 * @return
	 */
	@Query("select t from TodoEntity t where t.tno = :tno")
	TodoDTO getDTO(@Param("tno") Long tno);
	
	
	
	
	
	
	
}
