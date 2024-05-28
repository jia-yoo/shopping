package com.example.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shopping.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	@Query(value="select * from cart where mno = :mno", nativeQuery=true)
	List<Cart> findAllByMno(@Param("mno") Long mno);
	
	@Query(value="delete from cart where mno = :mno and pno = :pno", nativeQuery=true)
	void deleteByMnoAndPno(@Param("mno") Long mno, @Param("pno") Long pno);
}
