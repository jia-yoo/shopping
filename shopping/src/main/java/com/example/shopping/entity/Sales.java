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
@Table(name="sales")
public class Sales extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long sno;
	
	@ManyToOne
	@JoinColumn(name="pno", referencedColumnName = "pno")
	private Product product;
	
	@Column(nullable=false, length=20)
	private int sQuan;
	
	@ManyToOne
	@JoinColumn(nullable=false, name="mno", referencedColumnName = "mno")
	private Member member;
	
	@Column(nullable=false, length=20)
	private String orderNo;


}
