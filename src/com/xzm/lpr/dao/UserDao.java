package com.xzm.lpr.dao;


import static com.xzm.lpr.util.common.LprConstants.USERTABLE;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.provider.UserDynaSqlProvider;

public interface UserDao {

	@InsertProvider(type=UserDynaSqlProvider.class,method="insertUser")
	Integer save(User user);
	
	@Delete(" delete from "+USERTABLE+" where loginname = #{loginname} ")
	Integer deleteByLogin(String loginname);
	
	@Select("select * from "+USERTABLE+" where loginname = #{loginname} and password = #{password}")
	User selectByLoginnameAndPassword(
			@Param("loginname") String loginname,
			@Param("password") String password);
	
	@SelectProvider(type=UserDynaSqlProvider.class,method="selectWhitParam")
	List<User> selectByPage(Map<String, Object> params);
	
	@SelectProvider(type=UserDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);	
	
	@UpdateProvider(type=UserDynaSqlProvider.class,method="updateUser")
	Integer update(User user);	
}
