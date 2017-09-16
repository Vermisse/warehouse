package org.warehouse.web.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.warehouse.util.*;
import org.warehouse.web.dao.order.*;
import org.warehouse.web.service.*;

@Controller
@RequestMapping("order")
public class OrderController {
	
	@Autowired
	private OrderMapper mapper;

	@Autowired
	private OrderService service;

	@RequestMapping("list")
	String list(String id, Integer status, String create_date, Page page, Model model) {
		List<Map<String, Object>> list = mapper.queryList(id, status, create_date, page);
		int count = mapper.queryCount(id, status, create_date);
		page.setCount(count);

		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "order/list";
	}
	
	@RequestMapping("upload")
	String upload(@RequestParam("file") MultipartFile file, Model model) {
		try{
			service.save(file.getInputStream());
			return "redirect:/order/list.html";
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "order/list";
		}
	}
}