package org.zerock.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.dto.TodoDTO;
import org.zerock.dto.TodoListDTO;
import org.zerock.entity.TodoEntity;



@DataJpaTest //  JPA와 관련된 컴포넌트만 로드하여 테스트
// 단인 Entity에 대한 테스트 시 사용
// 연관 Entity는 문제 발생함

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// 임베디드 데이터베이스(H2) 대신 실제 데이터베이스를(Maria DB) 사용하도록 설정
// -> NONE으로 설정안하면 H2 DB로 연결

@Transactional(propagation = Propagation.NOT_SUPPORTED)
// 테스트 메서드에서 트랜잭션을 비활성화(트랜잭션 없이 테스트가 실행)

public class TodoRepositoryTests {

	@Autowired
	private TodoRepository todoRepository;
	
	/**
	 * 
	 */
//	@Test
	public void test1() {
		System.out.println(todoRepository.getClass().getName());
		// jdk.proxy2.$Proxy109
		// TodoRepository 인터페이스를 Proxy가 구현
	}
	
	/**
	 *  샘플 데이터 100개 삽입 테스트
	 */
//	@Test
	public void testInsert() {
		for(int i=0 ; i<100 ; i++) {
			TodoEntity entity = TodoEntity.builder()
								.title("Test" + i)
								.writer("user" + i)
								.dueDate(LocalDate.now())
								.build();
			
			todoRepository.save(entity); 
			// EntityManager에 샘플 데이터를 save(insert)
			// -> EntityManager는 Entit내용을 DB와 동기화 하려고 하기 때문에
			//   기존 테이블에 없던 샘플 데이터 100개가 동기화 되면서 삽입됨
			
			System.out.println(entity);
		}
	}
	
	// @Transactional이 없을 때 : 같은 SQL있어도 구분 없이 SELECT가 모두 수행됨 (SELECT 2회 수행)
	// -> 클래스 레벨 @Transactional에 Propagation.NOT_SUPPORTED가 선언되어 있어
	//    SQL 수행 될 때 마다 트랜잭션이 닫혀 이전 SQL 내용이 기억되지 않음
	
	
	// @Transactional이 있을 때 : 같은 SQL이 있으면 중복되는 SQL을 추가 수행하지 않음(SELECT 1회 수행)
	// -> 메서드 레벨에 @Transactional 작성 시 
	//    첫 번째 SELECT시 DB에서 조회한 결과가 트랜잭션에 저장됨
	//    두 번째 SELECT시 DB에서 조회하는게 아닌 트랜잭션에 저장된 이전 SQL 결과를 읽어옴
	
	// ** Dirty Checking **
	//
	
	@Test
	@Transactional
	public void testRead() {
		Long tno = 55L;
		
		// todoRepository.findById(tno) : PK가 일치하는 행을 조회
		java.util.Optional<TodoEntity> result = todoRepository.findById(tno);
		System.out.println(result.get());
		
		System.out.println("--------------------------------------------------");
		
		java.util.Optional<TodoEntity> result2 = todoRepository.findById(tno);
		System.out.println(result2.get());
		
	}
	
//	@Test
	@Transactional
	@Commit
	public void testDelete() {
		Long tno = 54L;
		
		// 1) 
		java.util.Optional<TodoEntity> result = todoRepository.findById(tno);
		TodoEntity entity = result.get();
		System.out.println(entity);
		
		System.out.println("--------------------------------------------------");
		
		todoRepository.delete(entity);
		
	}
	
	
	@Test
	@Transactional
	@Commit
	public void testUpdate() {
		Long tno = 55L;
		
		java.util.Optional<TodoEntity> result = todoRepository.findById(tno);
		TodoEntity entity = result.get();
		System.out.println(entity);
		
		// title : Test55

		entity.addTitle("55.................");
		// @Transactional이 존재 == Dirty Checking 수행
		// -> select된 Entity의 값을 변경 
		// -> 변경된 Entity의 값과 DB를 비교하여
		//    다른 부분이 있다면 동기화를 위해 자동으로 해당 부분 update가 수행됨
		
	}
	
	
	// 페이징 처리 (페이지는 0부터 시작)
	@Test
	public void testPaging() {
		
		// page, size, sort
		
		
		// org.springframework.data.domain.Pageable
		Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
		// tno 내림차순으로 0번(1페이지) 분량 10개 행만 조회
		
		// org.springframework.data.domain.Page
		Page<TodoEntity> result = todoRepository.findAll(pageable);
		
		// ** findAll() 호출 시 파라미터가 Pageable 이면
		//   반환 되는 값은 Page 타입이다!! **
		
		// ** 페이징 select 시 특이하게 count query가 같이 수행됨!
		// -> 조회하려는 데이터 수가 테이블의 총 개수보다 적으면 count query 수행 X
	}
	
	
	
	// ** 페이징 처리 결과를 DTO에 옮겨서 조회 **
	@Test
	public void testList() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
		
		Page<TodoDTO>  result = todoRepository.list1(pageable);
		
		System.out.println(result);
		result.getContent().forEach(dto -> System.out.println(dto));
	}
	
	// -------------------------------------------------------------------------------------
	
	@Test
	public void testListSearch() {
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
		Page<TodoDTO> result = todoRepository.listSearch(pageable);
		result.getContent().forEach(dto -> System.out.println(dto));
	}
	
	
	// -------------------------------------------------------------------------------------

	// todo 목록(tno, title, reveiwCout) 조회 테스트
	@Test
	public void testListSearchCount() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
		
		Page<TodoListDTO> result = todoRepository.listSearchCount(pageable);
		
		result.getContent().forEach(dto -> System.out.println(dto));
	}
	
	
	
	
}
