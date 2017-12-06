package org.warehouse.web.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.warehouse.util.*;
import org.warehouse.web.dao.product.*;
import org.warehouse.web.service.*;

@Controller
@RequestMapping("product")
public class ProductController {
	
	@Autowired
	private ProductMapper mapper;

	@Autowired
	private ProductService service;
	
	@RequestMapping("list")
	String list(String id, String create_time, String productName, Page page, Model model) {
		id = id == null ? null : id.trim();
		productName = productName == null ? null : productName.trim();
		List<Map<String, Object>> list = mapper.queryList(id, create_time, productName, page);
		int count = mapper.queryCount(id, create_time, productName);
		page.setCount(count);

		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "product/list";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("upload")
	String upload(@RequestParam("file") MultipartFile file, Model model, HttpSession session) {
		try{
			Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
			int userId = Integer.parseInt(String.valueOf(user.get("id")));
			service.save(file.getInputStream(), userId);
			return "redirect:/product/list.html";
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "product/list";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("edit")
	@ResponseBody
	String edit(String id, int count, int current_count, String name, int del, Model model, HttpSession session) {
		Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
		String userId = String.valueOf(user.get("id"));
		int result = mapper.editProductById(id, count, current_count, name, userId, del);
		if(result < 1) {
			return "{\"err\":1001}";
		}
		return "{\"err\":0}";
	}
	
	@GetMapping("add")
	String addProduct(Model model) {
		return "product/add";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("add")
	String addProduct(String id, int count, String name, Model model, HttpSession session) {
		if(mapper.checkProduct(id) != null) {
			Page page = new Page();
			list(id, null, null, page, model);
			return "product/list";
		}
		Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
		int userId = Integer.parseInt(String.valueOf(user.get("id")));
		int result = mapper.addProduct(id, name, count, userId);
		if(result == 0) {
			model.addAttribute("msg", "保存失败！");
			return "product/add";
		}
		return "redirect:/product/list.html";
	}
}