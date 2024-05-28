package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.shopping.entity.Product;
import com.example.shopping.entity.Sales;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.SalesRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private ProductRepository prodRepo;
	
	
	@RequestMapping("/shop")
	private void userProdList(Model model) {
		List<Product> list = prodRepo.findAll();
		model.addAttribute("list", list);
	}
	
	@RequestMapping("/prod_detail")
	private void userProdDetail(@RequestParam("pno") Long pno, Model model) {
		Product prod = prodRepo.findById(pno).get();
		model.addAttribute("prod", prod);
	}

}
