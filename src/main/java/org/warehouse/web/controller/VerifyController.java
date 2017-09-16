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
	
	@RequestMapping(value = "order", method = RequestMethod.GET)
	String order() {
		return "verify/order";
	}
	
	@RequestMapping(value = "order", method = RequestMethod.POST)
	String order(String id, Model model, HttpSession session) {
		Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
		Map<String, Object> order = mapper.checkOrder(id);
		if(order == null) {
			model.addAttribute("msg", "订单不存在！");
			return "verify/order";
		}
		if ((Integer) order.get("status") == 1) {
			return product(id, null, new String[0], model);
		}
		if ((Integer) order.get("status") == 2 && !order.get("accept").equals(user.get("id"))) {
			model.addAttribute("msg", "该订单已被其他人处理！");
		}
		if ((Integer) order.get("status") == 3) {
			model.addAttribute("msg", "该订单已处理完成！");
		}
		return "verify/order";
	}
	
	@RequestMapping("product")
	String product(String order_id, String product_id, String[] ids, Model model) {
		List<Map<String, Object>> list = mapper.queryProducts(order_id);
		if (product_id != null) {
			int state = 0;
			ids = ids == null ? new String[0] : ids;
			for (Map<String, Object> x : list) {
				for (String id : ids) {
					if (state == 0 && product_id.equals(id))
						state = 1;
					if (x.get("product_id").equals(id)) {
						x.put("finish", 1);
						break;
					}
				}
				if (state == 0 && x.get("product_id").equals(product_id)) {
					x.put("finish", 1);
					state = 2;
				}
			}
			switch (state) {
			case 0:
				model.addAttribute("msg", "该订单无此产品！");
				break;
			case 1:
				model.addAttribute("msg", "该产品已扫描！");
				break;
			case 2:
				String[] temp = new String[ids.length + 1];
				for (int i = 0; i < ids.length; i++)
					temp[i] = ids[i];
				temp[ids.length] = product_id;
				ids = temp;
				break;
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("ids", ids);
		model.addAttribute("order_id", order_id);
		return "verify/product";
	}
}