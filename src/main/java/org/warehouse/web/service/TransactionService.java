package org.warehouse.web.service;

import java.io.*;
import java.text.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
public class TransactionService {

	@Transactional
	public void saveOrder(InputStream is) throws Exception {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		
		try {
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null)
					continue;

				// 循环行Row
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null)
						continue;

					// order = new Order();
					HSSFCell detail = hssfRow.getCell(0);
					HSSFCell express = hssfRow.getCell(1); // 快递
					HSSFCell orderTime = hssfRow.getCell(2); // 订单时间
					HSSFCell orderNO = hssfRow.getCell(3); // 订单id
					// order.setDetail(getValue(detail));
					// order.setOrderNO(getValue(orderNO));
					// order.setOrderTime(getValue(orderTime));
					// order.setExpress(getValue(express));
					String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					// order.setCreateTime(time);
					// list.add(order);
				}
			}
		} finally {
			hssfWorkbook.close();
		}
	}
}