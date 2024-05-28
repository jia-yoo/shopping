package com.example.shopping.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.shopping.entity.Product;
import com.example.shopping.repository.ProductRepository;


@Controller
@RequestMapping("/admin")
public class AdminController {

	
	@Autowired
	private ProductRepository productRepo;
	private final Path rootLocation = Paths.get("/upload");
	
	@RequestMapping("/admin_main")
	private void root() {
	}
	@RequestMapping("/prod_regForm")
	private void prodRegForm() {
	}
	@RequestMapping("/prod_regist")
	private String prodRegist(@RequestParam("img") MultipartFile file, Product product) {
		 try {
	            // 만약 업로드할 폴더 없으면 만들기
	            if (!Files.exists(rootLocation)) {
	                Files.createDirectories(rootLocation);
	            }

	            if (file != null && !file.isEmpty()) {
	                // 파일업로드
	                String originalFilename = file.getOriginalFilename();
	                String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
	                String filename = UUID.randomUUID().toString() + extension;
	                Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();

	                // 파일이 이미 존재하면 덮어쓰기 또는 다른 처리를 해야 할 수 있음
	                Files.copy(file.getInputStream(), destinationFile);

	                String filePath = destinationFile.toString();

	                // User 엔티티에 파일 정보 설정
	                product.setFileName(filename);
	                product.setFilePath(filePath);
	                product.setFileSize(file.getSize());

	            
	            }
	            // User 엔티티 저장
                productRepo.save(product);
	        } catch (IOException e) {
	            throw new RuntimeException("파일을 업로드 할 수 없습니다!", e);
	        }
		
		return "redirect:/admin/prod_list";
	}

	@RequestMapping("/prod_list")
	private void prodList(Model model) {
		List<Product> list = productRepo.findAll();
		model.addAttribute("list", list);
	}
	
	@RequestMapping("/prod_detail")
	private void prodDetail(@RequestParam("pno") Long pno, Model model) {
		Product prod = productRepo.findById(pno).get();
		model.addAttribute("prod", prod);
	}
	
	@RequestMapping("/prod_edit")
	private void prodEdit(@RequestParam("pno") Long pno, Model model) {
		Product prod = productRepo.findById(pno).get();
		model.addAttribute("prod", prod);
	}
	
	@RequestMapping("/prod_update")
	private String prodUpdate(@RequestParam("pno") Long pno, @RequestParam("img") MultipartFile file, Product product) {
		 try {
			 System.out.println(product);
	            // 만약 업로드할 폴더 없으면 만들기
	            if (!Files.exists(rootLocation)) {
	                Files.createDirectories(rootLocation);
	            }

	            if (file != null && !file.isEmpty()) {
	                // 파일업로드
	                String originalFilename = file.getOriginalFilename();
	                String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
	                String filename = UUID.randomUUID().toString() + extension;
	                Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();

	                // 파일이 이미 존재하면 덮어쓰기 또는 다른 처리를 해야 할 수 있음
	                Files.copy(file.getInputStream(), destinationFile);

	                String filePath = destinationFile.toString();

	                // User 엔티티에 파일 정보 설정
	                product.setFileName(filename);
	                product.setFilePath(filePath);
	                product.setFileSize(file.getSize());

	            
	            }
	            // User 엔티티 저장
                productRepo.save(product);
	        } catch (IOException e) {
	            throw new RuntimeException("파일을 업로드 할 수 없습니다!", e);
	        }
		
		 return "redirect:/admin/prod_list";
	}
	@RequestMapping("/prod_delete")
	private String prodDelete(@RequestParam("pno") Long pno) {
		productRepo.deleteById(pno);
		return "redirect:/admin/prod_list";
	}
}
