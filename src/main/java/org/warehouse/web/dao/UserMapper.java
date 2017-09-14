package org.warehouse.web.dao;

import java.util.*;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

	Map<String, Object> queryUser(@Param("name") String name, @Param("password") String password);

	int addUser(@Param("name") String name, @Param("password") String password);
}