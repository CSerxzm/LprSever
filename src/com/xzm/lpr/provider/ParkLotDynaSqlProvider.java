package com.xzm.lpr.provider;

import static com.xzm.lpr.util.common.LprConstants.PARKLOTTABLE;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import com.xzm.lpr.domain.ParkLot;

public class ParkLotDynaSqlProvider {
	
	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(PARKLOTTABLE);
			}
		}.toString();

		return sql;
	}
	
	public String count(Map<String, Object> params){
		return new SQL(){
			{
				SELECT("count(*)");
				FROM(PARKLOTTABLE);
			}
		}.toString();
	}
	
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
				if(parkLot.getActivitycost_per()!= null){
					SET(" activitycost_per = #{activitycost_per} ");
				}
				if(parkLot.getMonthcost()!= null){
					SET(" monthcost = #{monthcost} ");
				}
				if(parkLot.getQuartercost()!= null){
					SET(" quartercost = #{quartercost} ");
				}
				if(parkLot.getHalfyearcost()!= null){
					SET(" halfyearcost = #{halfyearcost} ");
				}
				if(parkLot.getYearcost()!= null){
					SET(" yearcost = #{yearcost} ");
				}
				WHERE(" id = #{id} ");
			}
		}.toString();
	}
}
