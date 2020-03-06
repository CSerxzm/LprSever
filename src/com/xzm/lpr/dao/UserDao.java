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

/**   
 * @Description: UserMapper接口
 * <br>网站：<a href="http://www.fkit.org">疯狂Java</a> 
 * @author 肖文吉	36750064@qq.com   
 * @version V1.0   
 */
public interface UserDao {

	// 根据登录名和密码查询员工
	@Select("select * from "+USERTABLE+" where loginname = #{loginname} and password = #{password}")
	User selectByLoginnameAndPassword(
			@Param("loginname") String loginname,
			@Param("password") String password);
	
	// 根据loginname删除用户
	@Delete(" delete from "+USERTABLE+" where loginname = #{loginname} ")
	void deleteByLogin(String loginname);
		
	// 动态修改用户
	@UpdateProvider(type=UserDynaSqlProvider.class,method="updateUser")
	Integer update(User user);
		
	// 动态查询
	@SelectProvider(type=UserDynaSqlProvider.class,method="selectWhitParam")
	List<User> selectByPage(Map<String, Object> params);
	
	// 根据参数查询用户总数
	@SelectProvider(type=UserDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);
	
	// 动态插入用户
	@InsertProvider(type=UserDynaSqlProvider.class,method="insertUser")
	Integer save(User user);
	
}
