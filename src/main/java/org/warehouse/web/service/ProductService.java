package org.warehouse.web.service;

import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.web.dao.product.ProductMapper;

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
					String name = getCell(hssfRow, 0);
					String id =  getCell(hssfRow, 1);
					
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
	
	public String getCell(HSSFRow hssfRow, int i) {
		String str = "";
		try {
			str = hssfRow.getCell(i).getStringCellValue().trim();
		} catch (Exception e) {
			str = String.valueOf((int) hssfRow.getCell(i).getNumericCellValue()).replace(".0", "").trim();
		}
		return str;
	}
}