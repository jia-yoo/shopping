package com.example.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.shopping.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query(value="SELECT * FROM product ORDER BY regdate DESC LIMIT 3;", nativeQuery=true)
	List<Product> findThreeLatestProduct();
}
