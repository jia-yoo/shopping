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
@Table(name="cart")
public class Cart extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long cno;
	
	@ManyToOne
	@JoinColumn(name="pno", referencedColumnName = "pno")
	private Product product;
	
	@ManyToOne
	@JoinColumn(nullable=false, name="mno", referencedColumnName = "mno")
	private Member member;
	
	@Column(nullable=false)
	private int cQuan;

}
