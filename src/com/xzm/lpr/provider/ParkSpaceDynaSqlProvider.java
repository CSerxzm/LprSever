package com.xzm.lpr.provider;

import static com.xzm.lpr.util.common.LprConstants.PARKSPACETABLE;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

import com.xzm.lpr.domain.Notice;
import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.domain.User;

public class ParkSpaceDynaSqlProvider {

	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(PARKSPACETABLE);
				if(params.get("operate") != null){
					String operate = (String)params.get("operate");
					if(operate.equals("order")){
						WHERE("  idle='是'  or (idle='否'  and rentornot='是')  ");
					}
				}
				if(params.get("keyword") != null){
					String keyword = (String)params.get("keyword");
					WHERE("  name like '%"+keyword+"%' ");
				}
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}	

	public String count(Map<String, Object> params){
		return new SQL(){
			{
				SELECT("count(*)");
				FROM(PARKSPACETABLE);
				if(params.get("operate") != null){
					String operate = (String)params.get("operate");
					if(operate.equals("order")){
						WHERE("  idle='是'  or (idle='否'  and rentornot='是')  ");
					}
				}
				if(params.get("keyword") != null){
					String keyword = (String)params.get("keyword");
					WHERE("  name like '%"+keyword+"%' ");
				}
			}
		}.toString();
	}	
	
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
				if(parkSpace.getState() != null && !parkSpace.getState().equals("")){
					VALUES("state", "#{state}");
				}
				if(parkSpace.getIdle() != null && !parkSpace.getIdle().equals("")){
					VALUES("idle", "#{idle}");
				}
				if(parkSpace.getHire_start_date() != null && !parkSpace.getHire_start_date().equals("")){
					VALUES("hire_start_date", "#{hire_start_date}");
				}
				if(parkSpace.getHire_stop_date() != null && !parkSpace.getHire_stop_date().equals("")){
					VALUES("hire_stop_date", "#{hire_stop_date}");
				}
				if(parkSpace.getRentornot() != null && !parkSpace.getRentornot().equals("")){
					VALUES("rentornot", "#{rentornot}");
				}
			}
		}.toString();
	}
	
	public String updateParkSpace(ParkSpace parkSpace){
		return new SQL(){
			{
				UPDATE(PARKSPACETABLE);
				if(parkSpace.getName() != null){
					SET(" name = #{name} ");
				}
				if(parkSpace.getState() != null){
					SET(" state = #{state} ");
				}
				if(parkSpace.getIdle()!= null){
					SET(" idle = #{idle} ");
				}
				if(parkSpace.getHire_start_date()!= null && !parkSpace.getHire_start_date().equals("")){
					SET(" hire_start_date = #{hire_start_date} ");
				}
				if(parkSpace.getHire_stop_date()!= null && !parkSpace.getHire_stop_date().equals("")){
					SET(" hire_stop_date = #{hire_stop_date} ");
				}
				if(parkSpace.getRentornot()!= null){
					SET(" rentornot = #{rentornot} ");
				}
				WHERE(" id = #{id} ");
			}
		}.toString();
	}
}
