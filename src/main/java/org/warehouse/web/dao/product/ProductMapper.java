package org.warehouse.web.dao.product;

import java.util.*;

import org.apache.ibatis.annotations.*;
import org.warehouse.util.*;

public interface ProductMapper {

	int addProduct(@Param("id") String id,
			@Param("name") String name,
			@Param("count") Integer count,
			@Param("accept") Integer accept);
	
	Map<String, Object> checkProduct(@Param("id") String id);
	
	List<Map<String, Object>> queryList(@Param("id") String id,
			@Param("create_time") String create_time,
			@Param("name") String name,
			@Param("page") Page page);
	
	int queryCount(@Param("id") String id,
			@Param("create_time") String create_time,
			@Param("name") String name);
	
	int updateProduct(@Param("id") String id,
			@Param("count") Integer count,
			@Param("current_count") Integer current_count);

	int editProductById(@Param("id") String id,
			@Param("count") Integer count,
			@Param("current_count") Integer current_count,
			@Param("name") String name,
			@Param("accept") String accept,
			@Param("del_state") Integer del_state);
}