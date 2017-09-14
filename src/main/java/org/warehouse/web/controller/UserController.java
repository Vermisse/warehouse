package org.warehouse.web.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.warehouse.web.dao.*;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserMapper mapper;
	
	@RequestMapping("list")
	String list() {
		return "user/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	String add() {
		return "user/add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	String add(String name, String password, Model model) {
		if(mapper.queryUser(name, null) != null) {
			model.addAttribute("msg", "帐号已存在！");
			return "/user/add";
		}
		int result = mapper.addUser(name, password);
		if(result == 0) {
			model.addAttribute("msg", "保存失败！");
			return "/user/add";
		}
		return "redirect:/user/list.html";
	}
}