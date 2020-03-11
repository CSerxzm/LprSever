package com.xzm.lpr.dao;

import static com.xzm.lpr.util.common.LprConstants.PARKSPACETABLE;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;


import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.provider.ParkSpaceDynaSqlProvider;

public interface ParkSpaceDao {
		
	@InsertProvider(type=ParkSpaceDynaSqlProvider.class,method="insertParkSpace")
	Integer save(ParkSpace parkSpace);
	
	@Delete(" delete from "+PARKSPACETABLE+" where id = #{id} ")
	Integer deleteById(int id);
	
	@SelectProvider(type=ParkSpaceDynaSqlProvider.class,method="selectWhitParam")
	List<ParkSpace> selectByPage(Map<String, Object> params);
	
	@SelectProvider(type=ParkSpaceDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);
	
	@UpdateProvider(type=ParkSpaceDynaSqlProvider.class,method="updateParkSpace")
	Integer update(ParkSpace parkSpace);
}
