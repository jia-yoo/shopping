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
@Table(name="product")
public class Product extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long pno;
	
	@Column(nullable=false, length=20)
	private String pName;
	@Column(nullable=false)
	private int pQuan;
	@Column(nullable=false, length=20)
	private int pPrice;
	@Column(nullable=false, length=100)
	private String status;
	@Column
	private String fileName;
	@Column
	private String filePath;
	@Column
	private Long fileSize;
	@Column(columnDefinition = "TEXT")
	private String description;
}
