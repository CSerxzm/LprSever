package com.xzm.lpr.dao;

import static com.xzm.lpr.util.common.LprConstants.TRARECORDTABLE;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.provider.TraRecordDynaSqlProvider;

public interface TraRecordDao {

	@InsertProvider(type=TraRecordDynaSqlProvider.class,method="insertTraRecord")
	Integer save(TraRecord traRecord);
	
	@Delete(" delete from "+TRARECORDTABLE+" where id = #{id} ")
	Integer deleteById(int id);
	
	@SelectProvider(type=TraRecordDynaSqlProvider.class,method="selectWhitParam")
	List<TraRecord> selectByPage(Map<String, Object> params);
	
	@SelectProvider(type=TraRecordDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);

	@UpdateProvider(type=TraRecordDynaSqlProvider.class,method="updateTraRecord")
	Integer update(TraRecord traRecord);
	
}
