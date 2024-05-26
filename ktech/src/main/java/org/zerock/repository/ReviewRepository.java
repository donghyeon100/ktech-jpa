package org.zerock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.dto.ReviewDTO;
import org.zerock.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{

	
	/** 조회된 ReviewEntity를 ReviewDTO로 변환
	 * @param rno
	 * @return
	 */
	@Query("select r from ReviewEntity r where r.rno = :rno")
	Optional<ReviewDTO> getDTO(@Param("rno") Long rno);
	
}
