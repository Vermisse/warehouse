package org.warehouse.web.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
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
	String add(String name, String password) {
		int result = mapper.addUser(name, password);
		return result == 0 ? "redirect:/user/add.html" : "redirect:/user/list.html";
	}
}