package org.warehouse.web.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.*;
import org.warehouse.web.service.*;

@Controller
@RequestMapping("order")
public class OrderController {

	@Autowired
	private TransactionService tx;

	@RequestMapping("upload")
	String upload(@RequestParam("file") CommonsMultipartFile file) {
		try{
			tx.saveOrder(file.getInputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
}