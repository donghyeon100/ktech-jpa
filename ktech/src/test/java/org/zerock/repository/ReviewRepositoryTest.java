package org.zerock.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.dto.ReviewDTO;
import org.zerock.entity.ReviewEntity;

@SpringBootTest // 연관 Entity에서는 해당 어노테이션 작성
public class ReviewRepositoryTest {
	
	@Autowired
	private ReviewRepository repository;
	
	// 리뷰 삽입 테스트
	@Test
	public void testInsert() {
		Long tno = 200L;
		
		ReviewDTO reviewDTO = ReviewDTO.builder()
				.score(40)
				.tno(tno)
				.build();
		
		ReviewEntity entity = reviewDTO.toEntity();
		
		repository.save(entity);
		
		// 연관 관계에 cascade가 없긴 때문에 
		// tno에 대한 검증(해당 tno가 tbl_todos에 존재하는지 확인하는 select)이 수행되지 않음
	}
	
	
	

}
