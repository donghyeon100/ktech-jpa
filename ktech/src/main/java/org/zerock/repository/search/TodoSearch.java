package org.zerock.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.dto.TodoDTO;
import org.zerock.dto.TodoListDTO;

public interface TodoSearch {
	
	Page<TodoDTO> listSearch(Pageable pageable);
	
	Page<TodoListDTO>  listSearchCount(Pageable pageable);
}
