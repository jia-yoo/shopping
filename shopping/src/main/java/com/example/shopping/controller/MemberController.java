package com.example.shopping.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
	String dateString = formatter.format(new Date());
	@RequestMapping("/mem_regForm")
	private void memRegForm() {
	}
	
	@RequestMapping("/mem_regist")
	private String memRegist(Member member) {
		member.setRole("ROLE_MEMBER");		
		memberRepo.save(member);
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
	//상세페이지에서 바로 주문할경우
	@RequestMapping("/prod_order")
	private @ResponseBody String memProdOrder(HttpServletRequest request, @RequestParam("pno") Long pno, @RequestParam("sQuan") int sQuan,Model model) {
		String userName = (String) request.getSession().getAttribute("id");
		Sales sale = new Sales();
		sale.setMember(memberRepo.findByUserName(userName).get());
		sale.setSQuan(sQuan);
		sale.setProduct(productRepo.findById(pno).get());
		
		sale.setOrderNo(dateString + userName);
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
	@RequestMapping("/prod_orderFromCart")
	private @ResponseBody String prodOrderFromCart(@RequestBody List<Map<String, Object>> orderItems) {
		 for (Map<String, Object> item : orderItems) {
	            String pno = (String) item.get("pno");
	            int quan = Integer.parseInt((String) item.get("quan"));
	            System.out.println("Product No: " + pno + ", Quantity: " + quan);
	            // 여기서 주문 항목에 대한 추가 처리 (예: 데이터베이스 저장 등)
	        }
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
