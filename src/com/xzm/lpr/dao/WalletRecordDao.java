package com.xzm.lpr.dao;

import static com.xzm.lpr.util.common.LprConstants.WALLETRECORDTABLE;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.xzm.lpr.domain.WalletRecord;
import com.xzm.lpr.provider.WalletRecordDynaSqlProvider;

public interface WalletRecordDao {

	@InsertProvider(type=WalletRecordDynaSqlProvider.class,method="insertWalletRecord")
	Integer save(WalletRecord walletRecord);
	
	@Delete(" delete from "+WALLETRECORDTABLE+" where id = #{id} ")
	Integer deleteById(int id);
	
	@SelectProvider(type=WalletRecordDynaSqlProvider.class,method="selectWhitParam")
	List<WalletRecord> selectByPage(Map<String, Object> params);
	
	@SelectProvider(type=WalletRecordDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);

	@UpdateProvider(type=WalletRecordDynaSqlProvider.class,method="updateWalletRecord")
	Integer update(WalletRecord walletRecord);
		
}
