package com.example.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shopping.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query(value="select * from member where user_name = :userName", nativeQuery=true)
	Optional<Member> findByUserName(@Param("userName") String userName);
}
