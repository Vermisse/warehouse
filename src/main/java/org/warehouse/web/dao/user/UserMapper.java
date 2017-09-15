package org.warehouse.web.dao.user;

import java.util.*;
import org.apache.ibatis.annotations.*;
import org.warehouse.util.*;

public interface UserMapper {

	Map<String, Object> queryUser(@Param("name") String name, @Param("password") String password);

	List<Map<String, Object>> queryList(@Param("name") String name,
			@Param("type") Integer type,
			@Param("page") Page page);
	
	int queryCount(@Param("name") String name, @Param("type") Integer type);

	int addUser(@Param("name") String name, @Param("password") String password);
	
	Map<String, Object> queryUserById(@Param("id") String id);

	int editPasswordById(@Param("id") String id,@Param("password") String password);
}