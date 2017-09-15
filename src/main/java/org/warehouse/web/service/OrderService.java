package org.warehouse.web.service;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.warehouse.web.dao.order.*;

@Service
public class OrderService {

	@Autowired
	private OrderMapper mapper;
	
	@Transactional
	public void save(InputStream is) throws Exception {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		
		try {
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null)
					continue;

				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null)
						continue;

					String product_ids = hssfRow.getCell(0).getStringCellValue();
					String product_names = hssfRow.getCell(1).getStringCellValue();
					String delivery = hssfRow.getCell(2).getStringCellValue();
					Date create_date = hssfRow.getCell(3).getDateCellValue();
					String order_id = hssfRow.getCell(4).getStringCellValue();
					
					String[] product_id = product_ids.split(",");
					String[] product_name = product_names.split(",");
					
					if(product_id.length != product_name.length)
						throw new RuntimeException("商品ID和商品名数量不一致");
					
					if(mapper.checkOrder(order_id) != null)
						continue;
					
					// 添加订单
					mapper.addOrder(order_id, delivery, create_date);
					for (int i = 0; i < product_id.length; i++)
						//添加商品
						mapper.addProduct(order_id, product_id[i], product_name[i]);
				}
			}
		} finally {
			hssfWorkbook.close();
		}
	}
}