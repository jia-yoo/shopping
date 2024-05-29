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
import com.example.shopping.entity.Review;
import com.example.shopping.entity.Sales;
import com.example.shopping.repository.CartRepository;
import com.example.shopping.repository.MemberRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.ReviewRepository;
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
	@Autowired
	private ReviewRepository reviewRepo;
	
	
	@RequestMapping("/mem_regist")
	private String memRegist(Member member) {
		member.setRole("ROLE_MEMBER");		
		memberRepo.save(member);
		return "redirect:/user/login";
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
			return "redirect:/user/login";
		}
		model.addAttribute("result", "noId");
		return "redirect:/user/login";
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
		
		//날짜+아이디로 유일한 주문번호 생성
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    	String dateString = formatter.format(new Date());
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
	private @ResponseBody String prodOrderFromCart(HttpServletRequest request, @RequestBody List<Map<String, Object>> orderItems) {
		String userName = (String) request.getSession().getAttribute("id"); 
		Long mno = memberRepo.findByUserName(userName).get().getMno();
		for (Map<String, Object> item : orderItems) {
	            Long pno = Long.parseLong((String) item.get("pno"));
	            int quan = Integer.parseInt((String) item.get("quan"));
	            //product테이블에서 pQuan이 주문하려는 quan보다 많은지 check
	            Product prod = productRepo.findById(pno).get();
	    		int pQuan = prod.getPQuan();
	    		if(pQuan >= quan) {
	    			 Sales sale = new Sales();
	 	            sale.setMember(memberRepo.findByUserName(userName).get());
	 	            sale.setSQuan(quan);
	 	            sale.setProduct(productRepo.findById(pno).get());
	 	        	//날짜+아이디로 유일한 주문번호 생성
	 	            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	 	        	String dateString = formatter.format(new Date());
	 	            sale.setOrderNo(dateString + userName);
	 	            //sales 테이블에 더해주기
	 	    		salesRepo.save(sale);
	 	    		
	 	    		//product테이블에서 pQuan 줄여주기
	 	    		int newQuan = pQuan-quan;
	 	    		prod.setPQuan(newQuan);
	 	    		productRepo.save(prod);
	 	    		
	 	    		//장바구니 내에 주문된 아이템 지워주기
	 	    		cartRepo.deleteByMnoAndPno(mno, pno);
	 	    		return "true";
	    		}else {
//	    			System.out.println("수량부족스");
//	    			String pname = prod.getPName();
//	    			Map<String,String> lessItem = new HashMap<>();
//	    			lessItem.put("pname", pname);
//	    			 // Gson 객체 생성
//	    	        Gson gson = new Gson();
//	    	        // Map을 JSON 문자열로 변환
//	    	        String json = gson.toJson(lessItem);
//	    	        System.out.println(json);
	    			return "false";
	    		}
	        }
		return "false";
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
		List<String> orderNoList = salesRepo.findOrderNoByMno(member.getMno());
		model.addAttribute("member", member );
		model.addAttribute("orderNoList", orderNoList );
	}
	@RequestMapping("/order_detail")
	private void orderDetail(@RequestParam("orderNo") String orderNo, Model model) {
		List<Sales> order = salesRepo.findByOrderNo(orderNo);
		model.addAttribute("order", order);
		model.addAttribute("orderNo", orderNo);
	}
	@RequestMapping("/cancel_order")
	private @ResponseBody String cancelOrder(@RequestParam("orderNo") String orderNo) {
		salesRepo.deleteByOrderNo(orderNo);
		return "true";
	}
	@RequestMapping("/reg_review")
	private String regReview(HttpServletRequest request, @RequestParam("pno") Long pno, @RequestParam("content") String content, Model model) {
		String userName = (String) request.getSession().getAttribute("id");
		Member member = memberRepo.findByUserName(userName).get();
		Long mno = member.getMno();
		Product prod = productRepo.findById(pno).get();
		Review review = new Review();
		review.setMember(member);
		review.setProduct(prod);
		review.setContent(content);

		//이 상품을 실제 구매한적있는지 체크
		if(!salesRepo.findByMnoAndPno(mno, pno).isEmpty()) {
			//우리사이트에서 실제 구매한적 있는 경우
			review.setActualPurchase(1);
		}else{
			//우리사이트에서 구매한적 없는 경우
			review.setActualPurchase(0);
		};
		//리뷰테이블에 등록
		reviewRepo.save(review);
		return "redirect:/user/prod_detail?pno="+pno;
	}
}
