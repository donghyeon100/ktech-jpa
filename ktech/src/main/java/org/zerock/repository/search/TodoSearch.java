package org.zerock.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.dto.TodoDTO;

public interface TodoSearch {
	
	Page<TodoDTO> listSearch(Pageable pageable);
}
