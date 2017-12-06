package org.warehouse.web.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.warehouse.web.dao.order.*;

@Controller
@RequestMapping("verify")
public class VerifyController {

	@Autowired
	private OrderMapper mapper;
	
	@GetMapping("order")
	String order(Model model, HttpSession session) {
		Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
		List<Map<String, Object>> list = mapper.queryUnfinish((Integer) user.get("id"));
		model.addAttribute("list", list);
		return "verify/order";
	}
	
	@PostMapping("order")
	String order(String id, Model model, HttpSession session) {
		Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
		Map<String, Object> order = mapper.checkOrder(id);
		if(order == null) {
			model.addAttribute("msg", "订单不存在！");
			return "verify/order";
		}
		if ((Integer) order.get("status") == 1) {
			mapper.accept(id, (Integer) user.get("id"), 1, 2);
			return product(id, null, new String[0], model, session);
		}
		if ((Integer) order.get("status") == 2) {
			if (order.get("accept").equals(user.get("id")))
				return product(id, null, new String[0], model, session);
			model.addAttribute("msg", "该订单已被其他人处理！");
		}
		if ((Integer) order.get("status") == 3) {
			List<Map<String, Object>> list = mapper.queryProducts(id);
			model.addAttribute("product", list);
			model.addAttribute("msg", "该订单已处理完成！");
		}
		return "verify/order";
	}
	
	@RequestMapping("product")
	String product(String order_id, String product_id, String[] ids, Model model, HttpSession session) {
		Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
		List<Map<String, Object>> list = mapper.queryProducts(order_id);
		ids = ids == null ? new String[0] : ids;
		int count = 0;
		boolean exist = false;
		List<String> idsList = new ArrayList<String>(Arrays.asList(ids));
		boolean commit = true;
		if (product_id != null) { // 不是第一次进入
			int state = 0;
			for (Map<String, Object> x : list) {
				for (int i = 0; i < idsList.size(); i++) {
					if (x.get("product_id").equals(idsList.get(i))) {
						x.put("finish", 1);
						idsList.remove(i);
						break;
					}
				}
				boolean finish = String.valueOf(x.get("finish")).equals("1");
				if (state == 0 && x.get("product_id").equals(product_id)) {
					if (finish) {
						exist = true;
					} else {
						x.put("finish", 1);
						state = 1;
						count++;
					}
				}
				if (x.get("product_id").equals("无"))
					commit = false;
				if (finish)
					count++;
			}
			switch (state) {
			case 0:
				model.addAttribute("msg", exist ? "该商品已扫描！" : "该订单无此商品！");
				break;
			case 1:
				String[] temp = new String[ids.length + 1];
				for (int i = 0; i < ids.length; i++)
					temp[i] = ids[i];
				temp[ids.length] = product_id;
				ids = temp;
				break;
			}
		}
		if(count == list.size()) {
			// 订单扫描完毕
			mapper.accept(order_id, (Integer) user.get("id"), 2, 3);
			
			if (commit) {
				return "redirect:/verify/order.html";
			} else {
				String msg = "订单[" + order_id + "]存在以下无编号商品，请再次核对：<br />";
				for (Map<String, Object> x : list) {
					if(x.get("product_id").equals("无"))
						msg += "[" + x.get("product_name") + "]<br />";
				}
				model.addAttribute("msg", msg);
				return "verify/result";
			}
		} else {
			model.addAttribute("list", list);
			model.addAttribute("ids", ids);
			model.addAttribute("order_id", order_id);
			return "verify/product";
		}
	}
}