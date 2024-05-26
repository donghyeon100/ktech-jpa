package org.zerock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.dto.TodoDTO;
import org.zerock.dto.TodoListDTO;
import org.zerock.entity.TodoEntity;
import org.zerock.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class TodoService {
	
	private final TodoRepository repository;
	
	/** INERT
	 * @param dto
	 * @return
	 */
	public Long register(TodoDTO dto) {
		TodoEntity entity = dto.toEntity();
		entity = repository.save(entity);
		return entity.getTno();
	}
	
	
	/** 1행 SELECT
	 * @param tno
	 * @return
	 */
	public TodoDTO read(Long tno) {
		return repository.getDTO(tno);
	}
	
	
	/** UPADTE
	 * @param dto
	 */
	public void modify(TodoDTO dto) {
		repository.save(dto.toEntity());
	}
	
	
	/** DELETE
	 * @param tno
	 */
	public void remove(Long tno) {
		repository.deleteById(tno);
	}
	
	
	/** 목록 조회
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TodoDTO> getList(int page, int size) {
		Pageable pageable = PageRequest.of(page-1, size, Sort.by("tno").descending());
		return repository.listSearch(pageable);
	}
	
	
	/** 목록 조회 + 리뷰 수 조회
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TodoListDTO> getListWithCount(int page, int size) {
		Pageable pageable = PageRequest.of(page-1, size, Sort.by("tno").descending());
		return repository.listSearchCount(pageable);
	}
	
	
	
}
