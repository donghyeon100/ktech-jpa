package org.zerock.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.dto.TodoDTO;
import org.zerock.dto.TodoListDTO;
import org.zerock.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("api/todos")
@RequiredArgsConstructor
@Log4j2
public class TodoController {

	private final TodoService todoService;
	
	
	@GetMapping("list")
	public ResponseEntity<Page<TodoDTO>> list() {
		return ResponseEntity.ok(todoService.getList(1, 10));
	}
	
	
	@GetMapping("list2")
	public ResponseEntity<Page<TodoListDTO>> listWithCount() {
		return ResponseEntity.ok(todoService.getListWithCount(1, 10));
	}
	
	
	@GetMapping("read/{tno}")
	public ResponseEntity<TodoDTO> todo(
			@PathVariable("tno") Long tno){
		return ResponseEntity.ok(todoService.read(tno));
	}
	
}
