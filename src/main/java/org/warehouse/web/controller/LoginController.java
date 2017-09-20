package org.warehouse.web.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.warehouse.web.dao.order.*;
import org.warehouse.web.dao.user.*;

@Controller
public class LoginController {

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private OrderMapper order;
	
	@RequestMapping("/")
	String index(String create_date, Model model, HttpSession session) {
		if (session.getAttribute("user") == null)
			return "login";
		
		order.queryPie(create_date).forEach(x -> model.addAttribute("v" + x.get("status"), x.get("cnt")));
		return "main";
	}

	@RequestMapping("/login.html")
	String login(Model model, String name, String password, HttpSession session) {
		if (name == null || password == null)
			return "redirect:/";

		Map<String, Object> user = mapper.queryUser(name, password);
		if (user == null) {
			model.addAttribute("msg", "账号不存在或密码错误！");
			return "login";
		}

		session.setAttribute("user", user);
		return "redirect:/";
	}
	
	@RequestMapping("/logout.html")
	String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
}