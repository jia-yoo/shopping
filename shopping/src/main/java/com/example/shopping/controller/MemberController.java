package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.shopping.entity.Cart;
import com.example.shopping.entity.Member;
import com.example.shopping.entity.Product;
import com.example.shopping.entity.Sales;
import com.example.shopping.repository.CartRepository;
import com.example.shopping.repository.MemberRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.SalesRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private SalesRepository salesRepo;
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CartRepository cartRepo;
	
	@RequestMapping("/mem_regForm")
	private void memRegForm() {
	}
	
	@RequestMapping("/mem_regist")
	private String memRegist(Member member) {
		memberRepo.save(null);
		return "redirect:/member/login";
	}
	
	@RequestMapping("/login")
	private void login() {
	}
	
	@RequestMapping("/mem_login")
	private String memLogin(HttpServletRequest request, @RequestParam("userName") String userName, @RequestParam("pw") String pw, Model model) {
		if(memberRepo.findByUserName(userName).isPresent()) {
			Member member = memberRepo.findByUserName(userName).get();
			if(member.getPw().equals(pw)) {
				request.getSession().setAttribute("id", userName);
				return "redirect:/";
			}
			model.addAttribute("result", "wrongPw");
			return "redirect:/member/login";
		}
		model.addAttribute("result", "noId");
		return "redirect:/member/login";
	}

	@RequestMapping("/logout")
	private String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/prod_order")
	private @ResponseBody String memProdOrder(HttpServletRequest request, @RequestParam("pno") Long pno, @RequestParam("sQuan") int sQuan,Model model) {
		String userName = (String) request.getSession().getAttribute("id");
		Sales sale = new Sales();
		sale.setMember(memberRepo.findByUserName(userName).get());
		sale.setSQuan(sQuan);
		sale.setProduct(productRepo.findById(pno).get());
		
		//sales 테이블에 더해주기
		salesRepo.save(sale);
	
		//product테이블에서 pQuan 줄여주기
		Product prod = productRepo.findById(pno).get();
		int pQuan = prod.getPQuan();
		int newQuan = pQuan-sQuan;
		prod.setPQuan(newQuan);
		productRepo.save(prod);
		return "true";
	}
	@RequestMapping("/cart")
	private void cart(HttpServletRequest request, Model model) {
		String userName = (String) request.getSession().getAttribute("id");
		Member member = memberRepo.findByUserName(userName).get();
		Long mno = member.getMno();
		List<Cart> cartList = cartRepo.findAllByMno(mno);
		model.addAttribute("cartList", cartList);
	}
	@RequestMapping("/addToCart")
	private  @ResponseBody String addToCart(HttpServletRequest request, @RequestParam("pno") Long pno, @RequestParam("sQuan") int sQuan, Model model) {
		String userName = (String) request.getSession().getAttribute("id");
		Cart cart = new Cart();
		cart.setMember(memberRepo.findByUserName(userName).get());
		cart.setCQuan(sQuan);
		cart.setProduct(productRepo.findById(pno).get());
		System.out.println(cart);
		//cart 테이블에 더해주기
		cartRepo.save(cart);
//	    int newCartNum = cartNum+1;
//		request.getSession().setAttribute("cartNum", newCartNum);
		return "true";
	}
	@RequestMapping("/deleteFromCart")
	private String deleteFromCart(HttpServletRequest request, @RequestParam("pno") Long pno) {
		String userName = (String) request.getSession().getAttribute("id");
		Member member = memberRepo.findByUserName(userName).get();
		Long mno = member.getMno();
		cartRepo.deleteByMnoAndPno(mno, pno);
		return "redirect:/member/cart";
	}
	@RequestMapping("/myPage")
	private void myPage(HttpServletRequest request, Model model) {
		String userName = (String) request.getSession().getAttribute("id");
		Member member = memberRepo.findByUserName(userName).get();
		model.addAttribute("member", member );
	}
}
