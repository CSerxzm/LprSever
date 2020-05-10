package com.xzm.lpr.provider;

import static com.xzm.lpr.util.common.LprConstants.TRARECORDTABLE;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;

public class TraRecordDynaSqlProvider {

	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(TRARECORDTABLE);
				if(params.get("user") != null){
					User user = (User)params.get("user");
					if(user.getLicenseplate() != null && !user.getLicenseplate().equals("")){
						WHERE("  licenseplate LIKE CONCAT ('%',#{user.licenseplate},'%') ");
					}
					if(user.getParkspace_id() != null && !user.getParkspace_id().equals("")){
						WHERE("  space_id = #{user.parkspace_id} ");
					}
				}
				if(params.get("keyword") != null){
					String keyword = (String)params.get("keyword");
					WHERE(" space_id like '%"+keyword+"%' or licenseplate like '%"+keyword+"%'");
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
				FROM(TRARECORDTABLE);
				if(params.get("user") != null){
					User user = (User)params.get("user");
					System.out.println("sql"+user.getLicenseplate());
					if(user.getLicenseplate() != null && !user.getLicenseplate().equals("")){
						WHERE("  licenseplate LIKE CONCAT ('%',#{user.licenseplate},'%') ");
					}
					if(user.getParkspace_id() != null && !user.getParkspace_id().equals("")){
						WHERE("  space_id = #{user.parkspace_id}");
					}
				}
				if(params.get("keyword") != null){
					String keyword = (String)params.get("keyword");
					WHERE(" space_id like '%"+keyword+"%' or licenseplate like '%"+keyword+"%'");
				}
			}
		}.toString();
	}

	public String updateTraRecord(TraRecord traRecord){
			
		return new SQL(){
			{
				UPDATE(TRARECORDTABLE);
				if(traRecord.getSpace_id()!= null){
					SET(" space_id = #{space_id} ");
				}
				if(traRecord.getLicenseplate()!= null){
					SET(" licenseplate = #{licenseplate} ");
				}
				if(traRecord.getDate_in()!= null && !traRecord.getDate_in().equals("")){
					SET(" date_in = #{date_in} ");
				}else {
					SET(" date_in = null ");
				}
				if(traRecord.getDate_out()!= null && !traRecord.getDate_in().equals("")){
					SET(" date_out = #{date_out} ");
				}else {
					SET(" date_out = null ");
				}
				if(traRecord.getCost()!= null){
					SET(" cost = #{cost} ");
				}
				WHERE(" id = #{id} ");
			}
		}.toString();
	}
	
	public String insertTraRecord(TraRecord traRecord) {
		return new SQL(){
			{
				INSERT_INTO(TRARECORDTABLE);
				if(traRecord.getSpace_id() != null && !traRecord.getSpace_id().equals("")){
					VALUES("space_id", "#{space_id}");
				}
				if(traRecord.getLicenseplate() != null && !traRecord.getLicenseplate().equals("")){
					VALUES("licenseplate", "#{licenseplate}");
				}
				if(traRecord.getDate_in() != null && !traRecord.getDate_in().equals("")){
					VALUES("date_in", "#{date_in}");
				}
				if(traRecord.getDate_out() != null && !traRecord.getDate_out().equals("")){
					VALUES("date_out", "#{date_out}");
				}
				if(traRecord.getCost() != null && !traRecord.getCost().equals("")){
					VALUES("cost", "#{cost}");
				}
			}
		}.toString();
	}
}
