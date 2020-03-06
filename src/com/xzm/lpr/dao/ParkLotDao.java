package com.xzm.lpr.dao;

import static com.xzm.lpr.util.common.LprConstants.PARKLOTTABLE;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.provider.ParkLotDynaSqlProvider;
import com.xzm.lpr.provider.ParkSpaceDynaSqlProvider;

public interface ParkLotDao {
		
	// 动态修改用户
	@UpdateProvider(type=ParkLotDynaSqlProvider.class,method="updateParkLot")
	Integer update(ParkLot parkLot);
		
	// 动态查询
	@SelectProvider(type=ParkLotDynaSqlProvider.class,method="selectWhitParam")
	List<ParkLot> selectByPage(Map<String, Object> params);
	
	@SelectProvider(type=ParkLotDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);
}
