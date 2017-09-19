package org.warehouse.web.service;

import java.io.*;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.warehouse.util.*;
import org.warehouse.web.dao.product.*;

@Service
public class ProductService {

	@Autowired
	private ProductMapper mapper;
	
	@Transactional
	public void save(InputStream is, int userId) throws Exception {
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
					
					String name = PoiUtil.getValue(hssfRow.getCell(0));
					String id = PoiUtil.getValue(hssfRow.getCell(1));
					
					Integer count = (int) hssfRow.getCell(2).getNumericCellValue();
					
					if(mapper.checkProduct(id) != null) {
						// 修改库存
						mapper.updateProduct(id, count, 0);
					} else {
						// 添加产品
						mapper.addProduct(id, name, count, userId);
					}
				}
			}
		} finally {
			hssfWorkbook.close();
		}
	}
}