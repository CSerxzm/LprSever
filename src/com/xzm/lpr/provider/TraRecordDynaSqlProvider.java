package com.xzm.lpr.provider;

import static com.xzm.lpr.util.common.LprConstants.TRARECORDTABLE;
import static com.xzm.lpr.util.common.LprConstants.USERTABLE;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

import com.xzm.lpr.domain.Notice;
import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;

public class TraRecordDynaSqlProvider {
	// 分页动态查询
	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(TRARECORDTABLE);
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
				FROM(TRARECORDTABLE);
			}
		}.toString();
	}
	
	// 动态更新
	public String updateTraRecord(TraRecord traRecord){
			
		return new SQL(){
			{
				UPDATE(TRARECORDTABLE);
				if(traRecord.getLicenseplate()!= null){
					SET(" licenseplate = #{licenseplate} ");
				}
				if(traRecord.getDate_in()!= null){
					SET(" date_in = #{date_in} ");
				}
				if(traRecord.getDate_out()!= null){
					SET(" date_out = #{date_out} ");
				}
				WHERE(" id = #{id} ");
			}
		}.toString();
	}
}
