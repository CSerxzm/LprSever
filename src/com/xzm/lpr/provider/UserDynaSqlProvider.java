package com.xzm.lpr.provider;

import static com.xzm.lpr.util.common.LprConstants.USERTABLE;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

import com.xzm.lpr.domain.User;

public class UserDynaSqlProvider {

	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(USERTABLE);
				if(params.get("user") != null){
					User user = (User)params.get("user");
					if(user.getLoginname() != null && !user.getLoginname().equals("")){
						WHERE("  loginname = #{user.loginname} ");
					}
					if(user.getPassword() != null && !user.getPassword().equals("")){
						WHERE("  password = #{user.password} ");
					}
				}
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}	

	public String count(Map<String, Object> params){
		String sql = new SQL(){
			{
				SELECT("count(*)");
				FROM(USERTABLE);
				if(params.get("user") != null){
					User user = (User)params.get("user");
					if(user.getLoginname() != null && !user.getLoginname().equals("")){
						WHERE("  loginname = #{user.loginname} ");
					}
					if(user.getPassword() != null && !user.getPassword().equals("")){
						WHERE("  password = #{user.password} ");
					}
				}
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}	
	
	public String insertUser(User user){
		return new SQL(){
			{
				INSERT_INTO(USERTABLE);
				if(user.getLoginname() != null && !user.getLoginname().equals("")){
					VALUES("loginname", "#{loginname}");
				}
				if(user.getPassword() != null && !user.getPassword().equals("")){
					VALUES("password", "#{password}");
				}
				//用于取消绑定的停车位
				if(user.getParkspace_id() != null && !user.getParkspace_id().equals("")){
					VALUES("parkspace_id", "#{parkspace_id}");
				}
				if(user.getLicenseplate() != null && !user.getLicenseplate().equals("")){
					VALUES("licenseplate", "#{licenseplate}");
				}
				if(user.getTelephone() != null && !user.getTelephone().equals("")){
					VALUES("telephone", "#{telephone}");
				}
				if(user.getAuthority() != null && !user.getAuthority().equals("")){
					VALUES("authority", "#{authority}");
				}
				if(user.getWallet() != null && !user.getWallet().equals("")){
					VALUES("wallet", "#{wallet}");
				}
			}
		}.toString();
	}
	// 动态更新
	public String updateUser(User user){
			
		return new SQL(){
			{
				UPDATE(USERTABLE);
				if(user.getPassword()!= null){
					SET(" password = #{password} ");
				}
				if(user.getParkspace_id()!= null){
					SET(" parkspace_id = #{parkspace_id} ");
				}
				if(user.getLicenseplate()!= null){
					SET(" licenseplate = #{licenseplate} ");
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
				if(user.getWallet()!= null){
					SET(" wallet = #{wallet} ");
				}				
				WHERE(" loginname = #{loginname} ");
			}
		}.toString();
	}
}
