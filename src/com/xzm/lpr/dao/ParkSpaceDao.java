package com.xzm.lpr.dao;

import static com.xzm.lpr.util.common.LprConstants.PARKSPACETABLE;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.provider.ParkLotDynaSqlProvider;
import com.xzm.lpr.provider.ParkSpaceDynaSqlProvider;

public interface ParkSpaceDao {
		
	// 动态修改用户
	@UpdateProvider(type=ParkSpaceDynaSqlProvider.class,method="updateParkSpace")
	Integer update(ParkSpace parkSpace);
		
	// 动态查询
	@SelectProvider(type=ParkSpaceDynaSqlProvider.class,method="selectWhitParam")
	List<ParkSpace> selectByPage(Map<String, Object> params);
	
	@SelectProvider(type=ParkSpaceDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);
	
	@Delete(" delete from "+PARKSPACETABLE+" where id = #{id} ")
	Integer deleteById(int id);
}
