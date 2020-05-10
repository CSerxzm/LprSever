package com.xzm.lpr.provider;

import static com.xzm.lpr.util.common.LprConstants.WALLETRECORDTABLE;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

import com.xzm.lpr.domain.User;
import com.xzm.lpr.domain.WalletRecord;

public class WalletRecordDynaSqlProvider {

	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(WALLETRECORDTABLE);
				if(params.get("user") != null){
					User user = (User)params.get("user");
					if(user.getLoginname() != null && !user.getLoginname().equals("")){
						WHERE("  name = #{user.loginname} ");
					}
				}
				if(params.get("keyword") != null){
					String keyword = (String)params.get("keyword");
					WHERE("  name like '%"+keyword+"%' or operation like '%"+keyword+"%'");
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
				FROM(WALLETRECORDTABLE);
				if(params.get("user") != null){
					User user = (User)params.get("user");
					if(user.getLoginname() != null && !user.getLoginname().equals("")){
						WHERE("  name = #{user.loginname} ");
					}
				}
				if(params.get("keyword") != null){
					String keyword = (String)params.get("keyword");
					WHERE("  name like '%"+keyword+"%' or operation like '%"+keyword+"%'");
				}
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}	
	
	public String insertWalletRecord(WalletRecord walletRecord){
		return new SQL(){
			{
				INSERT_INTO(WALLETRECORDTABLE);
				if(walletRecord.getDate_pay() != null && !walletRecord.getDate_pay().equals("")){
					VALUES("date_pay", "#{date_pay}");
				}
				if(walletRecord.getName() != null && !walletRecord.getName().equals("")){
					VALUES("name", "#{name}");
				}
				if(walletRecord.getOperation() != null && !walletRecord.getOperation().equals("")){
					VALUES("operation", "#{operation}");
				}
				if(walletRecord.getCost() != null && !walletRecord.getCost().equals("")){
					VALUES("cost", "#{cost}");
				}
			}
		}.toString();
	}
	// 动态更新
	public String updateWalletRecord(WalletRecord walletRecord){
			
		return new SQL(){
			{
				UPDATE(WALLETRECORDTABLE);
				if(walletRecord.getDate_pay()!= null){
					SET(" date_pay = #{date_pay} ");
				}
				if(walletRecord.getName()!= null){
					SET(" name = #{name} ");
				}
				if(walletRecord.getOperation()!= null){
					SET(" operation = #{operation} ");
				}
				if(walletRecord.getCost()!= null){
					SET(" cost = #{cost} ");
				}			
				WHERE(" id = #{id} ");
			}
		}.toString();
	}
}
