package com.example.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shopping.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query(value="select * from review where pno = :pno", nativeQuery=true)
	List<Review> findAllByPno(@Param("pno") Long pno);
}
