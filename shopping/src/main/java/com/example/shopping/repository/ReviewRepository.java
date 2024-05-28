package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

}
