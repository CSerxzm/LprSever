package com.xzm.lpr.provider;

import static com.xzm.lpr.util.common.LprConstants.USERTABLE;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

import com.xzm.lpr.domain.User;

public class UserDynaSqlProvider {
	// 分页动态查询
	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(USERTABLE);
				if(params.get("user") != null){
					User user = (User)params.get("user");
					if(user.getUsername() != null && !user.getUsername().equals("")){
						WHERE("  username LIKE CONCAT ('%',#{user.username},'%') ");
					}
				}
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}	
	// 动态查询总数量
	public String count(Map<String, Object> params){
		return new SQL(){
			{
				SELECT("count(*)");
				FROM(USERTABLE);
				if(params.get("user") != null){
					User user = (User)params.get("user");
					if(user.getUsername() != null && !user.getUsername().equals("")){
						WHERE(" username LIKE CONCAT ('%',#{user.username},'%') ");
					}
				}
			}
		}.toString();
	}	
	
	// 动态插入
	public String insertUser(User user){
		return new SQL(){
			{
				INSERT_INTO(USERTABLE);
				if(user.getUsername() != null && !user.getUsername().equals("")){
					VALUES("username", "#{username}");
				}
				if(user.getLoginname() != null && !user.getLoginname().equals("")){
					VALUES("loginname", "#{loginname}");
				}
				if(user.getPassword() != null && !user.getPassword().equals("")){
					VALUES("password", "#{password}");
				}
				if(user.getTelephone() != null && !user.getTelephone().equals("")){
					VALUES("telephone", "#{telephone}");
				}
				if(user.getAuthority() != null && !user.getAuthority().equals("")){
					VALUES("authority", "#{authority}");
				}
			}
		}.toString();
	}
	// 动态更新
	public String updateUser(User user){
			
		return new SQL(){
			{
				UPDATE(USERTABLE);
				if(user.getUsername() != null){
					SET(" username = #{username} ");
				}
				if(user.getPassword()!= null){
					SET(" password = #{password} ");
				}
				if(user.getCreateDate()!= null){
					SET(" createdate = #{createDate} ");
				}
				if(user.getTelephone()!= null){
					SET(" telephone = #{telephone} ");
				}
				if(user.getAuthority()!= null){
					SET(" authority = #{authority} ");
				}
				WHERE(" loginname = #{loginname} ");
			}
		}.toString();
	}
}
