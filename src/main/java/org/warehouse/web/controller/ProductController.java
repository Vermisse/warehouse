package org.warehouse.web.controller;

import java.util.*;

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
	String list(String id, String create_time, Page page, Model model) {
		List<Map<String, Object>> list = mapper.queryList(id, create_time, page);
		int count = mapper.queryCount(id, create_time);
		page.setCount(count);

		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "product/list";
	}
	
	@RequestMapping("upload")
	String upload(@RequestParam("file") MultipartFile file, Model model) {
		try{
			service.save(file.getInputStream());
			return "redirect:/product/list.html";
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "product/list";
		}
	}
	
	@RequestMapping("edit")
	@ResponseBody
	String edit(String id, int current_count, Model model) {
		int result = mapper.editProductById(id, current_count);
		if(result < 1) {
			return "{\"err\":1001}";
		}
		return "{\"err\":0}";
	}
}