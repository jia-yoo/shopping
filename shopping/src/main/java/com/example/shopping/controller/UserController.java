package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.entity.Product;
import com.example.shopping.entity.Review;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.ReviewRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private ProductRepository prodRepo;
	@Autowired
	private ReviewRepository reviewRepo;
	
	
	@RequestMapping("/shop")
	private void userProdList(Model model) {
		List<Product> list = prodRepo.findAll();
		model.addAttribute("list", list);
	}
	
	@RequestMapping("/prod_detail")
	private void userProdDetail(@RequestParam("pno") Long pno, Model model) {
		Product prod = prodRepo.findById(pno).get();
		List<Review> reviewList = reviewRepo.findAllByPno(pno);
		Long avg = reviewRepo.getAvgReview(pno);
		model.addAttribute("prod", prod);
		model.addAttribute("review", reviewList);
		model.addAttribute("avg", avg);
	}
	
	@RequestMapping("/login")
	private void login() {
	}
	
	@RequestMapping("/mem_regForm")
	private void memRegForm() {
	}

}
