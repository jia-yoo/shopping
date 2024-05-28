package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.entity.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long>{

}
