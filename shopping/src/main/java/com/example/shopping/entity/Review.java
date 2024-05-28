package com.example.shopping.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="review")
public class Review extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long rno;
	
	@ManyToOne
	@JoinColumn(nullable=false, name = "pno",referencedColumnName = "pno")
	private Product product;
	
	
	@ManyToOne
	@JoinColumn(nullable=false, name = "mno",referencedColumnName = "mno")
	private Member member;
	
	@Column(nullable=false, columnDefinition = "TEXT")
	private String content;
	
	@Column
	private int actualPurchase;

}
