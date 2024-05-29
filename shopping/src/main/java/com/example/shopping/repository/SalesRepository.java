package com.example.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shopping.entity.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long>{

	@Query(value="SELECT order_no FROM sales WHERE mno= :mno GROUP BY order_no ", nativeQuery=true)
	List<String> findOrderNoByMno(@Param("mno") Long mno);
	
	@Query(value="select * from sales where order_no = :orderNo", nativeQuery=true)
	List<Sales> findByOrderNo(@Param("orderNo") String orderNo);
	
	@Query(value="delete from sales where order_no = :orderNo", nativeQuery=true)
	void deleteByOrderNo(@Param("orderNo") String orderNo);
	
	@Query(value="SELECT * FROM sales WHERE MNO= :mno AND PNO= :pno", nativeQuery=true)
	List<Sales> findByMnoAndPno(@Param("mno") Long mno, @Param("pno") Long pno);
	
	@Query(value="SELECT pno FROM sales GROUP BY pno ORDER BY SUM(s_quan) DESC LIMIT 3", nativeQuery=true)
	List<Long> findThreeMostSoldItems();
}
