package com.example.shopping.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="member")
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long mno;
	
	@Column(nullable=false, length=20)
	private String name;
	@Column(nullable=false, length=20)
	private String userName;
	@Column(nullable=false, length=20)
	private String pw;
	@Column(nullable=false, length=20)
	private String role;
	@Column(nullable=false, length=50)
	private String addr;
	@Column(nullable=false, length=30)
	private String tel;

}
