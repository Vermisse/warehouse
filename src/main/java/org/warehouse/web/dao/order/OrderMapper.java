package org.warehouse.web.dao.order;

import java.util.*;

import org.apache.ibatis.annotations.*;
import org.warehouse.util.*;

public interface OrderMapper {

	int addOrder(@Param("id") String id,
			@Param("delivery") String delivery,
			@Param("create_date") Date create_date);
	
	int addProduct(@Param("order_id") String order_id,
			@Param("product_id") String product_id,
			@Param("product_name") String product_name);
	
	Map<String, Object> checkOrder(@Param("id") String id);
	
	List<Map<String, Object>> queryList(@Param("id") String id,
			@Param("status") Integer status,
			@Param("page") Page page);
	
	int queryCount(@Param("id") String id,
			@Param("status") Integer status);

	List<Map<String, Object>> queryProducts(@Param("order_id") String order_id);
}