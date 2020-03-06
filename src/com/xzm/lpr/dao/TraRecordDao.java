package com.xzm.lpr.dao;


import static com.xzm.lpr.util.common.LprConstants.TRARECORDTABLE;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.provider.TraRecordDynaSqlProvider;
import com.xzm.lpr.provider.UserDynaSqlProvider;

public interface TraRecordDao {
				
	@SelectProvider(type=TraRecordDynaSqlProvider.class,method="selectWhitParam")
	List<TraRecord> selectByPage(Map<String, Object> params);
	
	@SelectProvider(type=TraRecordDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);
	
	@Delete(" delete from "+TRARECORDTABLE+" where id = #{id} ")
	Integer deleteById(int id);
	
	@UpdateProvider(type=TraRecordDynaSqlProvider.class,method="updateTraRecord")
	Integer update(TraRecord traRecord);
	
}
