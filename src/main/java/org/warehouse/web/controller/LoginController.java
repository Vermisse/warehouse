package org.warehouse.web.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.warehouse.web.dao.*;

@Controller
public class LoginController {

	@Autowired
	private UserMapper mapper;
	
	@RequestMapping("/")
	String index(HttpSession session) {
		if (session.getAttribute("user") != null)
			return "main";
		return "login";
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