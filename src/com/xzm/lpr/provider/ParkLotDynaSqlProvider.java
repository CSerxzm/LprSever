package com.xzm.lpr.provider;

import static com.xzm.lpr.util.common.LprConstants.PARKLOTTABLE;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

import com.xzm.lpr.domain.Notice;
import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;

public class ParkLotDynaSqlProvider {
	// 分页动态查询
	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(PARKLOTTABLE);
			}
		}.toString();

		return sql;
	}
	
	// 动态查询总数量
	public String count(Map<String, Object> params){
		return new SQL(){
			{
				SELECT("count(*)");
				FROM(PARKLOTTABLE);
			}
		}.toString();
	}
	
	// 动态更新
	public String updateParkLot(ParkLot parkLot){
			
		return new SQL(){
			{
				UPDATE(PARKLOTTABLE);
				if(parkLot.getName()!= null){
					SET(" name = #{name} ");
				}
				if(parkLot.getAddress()!= null){
					SET(" address = #{address} ");
				}
				if(parkLot.getTelephone()!= null){
					SET(" telephone = #{telephone} ");
				}
				if(parkLot.getActivitynum()!= null){
					SET(" activitynum = #{activitynum} ");
				}
				if(parkLot.getFixationnum()!= null){
					SET(" fixationnum = #{fixationnum} ");
				}
				WHERE(" id = #{id} ");
			}
		}.toString();
	}
}
