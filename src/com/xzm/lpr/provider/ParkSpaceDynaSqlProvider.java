package com.xzm.lpr.provider;

import static com.xzm.lpr.util.common.LprConstants.PARKSPACETABLE;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.domain.User;

public class ParkSpaceDynaSqlProvider {
	// 分页动态查询
	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(PARKSPACETABLE);
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
				FROM(PARKSPACETABLE);
			}
		}.toString();
	}	
	
	// 动态插入
	public String insertParkSpace(ParkSpace parkSpace){
		return new SQL(){
			{
				INSERT_INTO(PARKSPACETABLE);
				if(parkSpace.getId() != null && !parkSpace.getId().equals("")){
					VALUES("id", "#{id}");
				}
				if(parkSpace.getName() != null && !parkSpace.getName().equals("")){
					VALUES("name", "#{name}");
				}
				if(parkSpace.getOwnner() != null && !parkSpace.getOwnner().equals("")){
					VALUES("ownner", "#{ownner}");
				}
				if(parkSpace.getHire_start_date() != null && !parkSpace.getHire_start_date().equals("")){
					VALUES("hire_start_date", "#{hire_start_date}");
				}
				if(parkSpace.getHire_stop_date() != null && !parkSpace.getHire_stop_date().equals("")){
					VALUES("hire_stop_date", "#{hire_stop_date}");
				}
				if(parkSpace.getRent_start_date() != null && !parkSpace.getRent_start_date().equals("")){
					VALUES("rent_start_date", "#{rent_start_date}");
				}
				if(parkSpace.getRent_stop_date() != null && !parkSpace.getRent_stop_date().equals("")){
					VALUES("rent_stop_date", "#{rent_stop_date}");
				}
				if(parkSpace.getRentornot() != null && !parkSpace.getRentornot().equals("")){
					VALUES("rentornot", "#{rentornot}");
				}
			}
		}.toString();
	}
	// 动态更新
	public String updateParkSpace(ParkSpace parkSpace){
			
		return new SQL(){
			{
				UPDATE(PARKSPACETABLE);
				if(parkSpace.getName() != null){
					SET(" name = #{name} ");
				}
				if(parkSpace.getOwnner()!= null){
					SET(" ownner = #{ownner} ");
				}
				if(parkSpace.getHire_start_date()!= null){
					SET(" hire_start_date = #{hire_start_date} ");
				}
				if(parkSpace.getHire_stop_date()!= null){
					SET(" hire_stop_date = #{hire_stop_date} ");
				}
				if(parkSpace.getRentornot()!= null){
					SET(" rentornot = #{rentornot} ");
				}
				if(parkSpace.getRent_start_date()!= null){
					SET(" rent_start_date = #{rent_start_date} ");
				}
				if(parkSpace.getRent_stop_date()!= null){
					SET(" rent_stop_date = #{rent_stop_date} ");
				}
				WHERE(" id = #{id} ");
			}
		}.toString();
	}
}
