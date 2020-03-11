package com.xzm.lpr.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.provider.ParkLotDynaSqlProvider;

public interface ParkLotDao {
			
	@SelectProvider(type=ParkLotDynaSqlProvider.class,method="selectWhitParam")
	List<ParkLot> selectByPage(Map<String, Object> params);
	
	@SelectProvider(type=ParkLotDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);
	
	@UpdateProvider(type=ParkLotDynaSqlProvider.class,method="updateParkLot")
	Integer update(ParkLot parkLot);
}
