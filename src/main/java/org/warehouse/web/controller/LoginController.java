package org.warehouse.web.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.warehouse.web.dao.*;

@Controller
public class LoginController {

	@Autowired
	private UserMapper mapper;
	
	@RequestMapping(value = "/")
	String login() {
		return "login";
	}

	@RequestMapping(value = "/login.html")
	String login(Model model, String name, String password) {
		if(name == null || password == null)
			return "redirect:/";
		
		Map<String, Object> user = mapper.queryUser(name, password);
		if(user == null){
			model.addAttribute("msg", "账号不存在或密码错误！");
		}else{
			model.addAttribute("msg", "登录成功！");
		}
		return "login";
	}
}