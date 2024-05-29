package com.example.shopping.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.shopping.entity.Product;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.SalesRepository;

@Controller
public class MainController {
	
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private SalesRepository salesRepo;
	
	
	@RequestMapping("/")
	private String root(Model model) {
		List<Product> latestList = productRepo.findThreeLatestProduct();
		List<Long> pnoList = salesRepo.findThreeMostSoldItems();
		List<Product> mostSoldList = new ArrayList<>();
		for (Long pno : pnoList) {
			mostSoldList.add(productRepo.findById(pno).get());
		}
		model.addAttribute("prodList", latestList);
		model.addAttribute("mostSoldList", mostSoldList);
		return "index";
	}
	@RequestMapping("/access-denied")
	private void accessDenied() {
	}
}
