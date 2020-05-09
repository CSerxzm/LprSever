package com.xzm.lpr.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xzm.lpr.domain.Notice;
import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.domain.WalletRecord;
import com.xzm.lpr.util.tag.PageModel;

public interface LprService {
	
	List<ParkLot> findParkLot();
	Integer updateParkLot(ParkLot parkLot);

	Integer addUser(User user);
	Integer removeUserByLogin(String loginname);
	User login(String loginname,String password);
	List<User> findUser(User user,PageModel pageModel);
	Integer updateUser(User user);
	Integer getParkSpace_idByLicenseplate(String licenseplate);

	Integer addTraRecord(TraRecord traRecord);
	Integer removeTraRecordById(int id);
	List<TraRecord> findTraRecord(User user,PageModel pageModel);
	Integer updateTraRecord(TraRecord traRecord);
	TraRecord getTraRecordDate_out(String licenseplate);

	Integer addParkSpace(ParkSpace parkSpace);
	Integer removeParkSpaceById(int id);
	List<ParkSpace> findParkSpace(User user,String operate,PageModel pageModel);
	Integer updateParkSpace(ParkSpace parkSpace);
	ParkSpace getParkSpaceOne();
	
	Integer rentParkSpace(ParkSpace parkSpace,User user);

	Integer addNotice(Notice notice);
	Integer removeNoticeById(Integer id);
	List<Notice> findNotice(Notice notice,PageModel pageModel);
	Notice findNoticeById(Integer id);
	List<Notice> findNotice(PageModel pageModel);
	Integer updateNotice(Notice notice);
	
	Integer addWalletRecord(WalletRecord walletRecord);
	List<WalletRecord> findWalletRecord(User user,PageModel pageModel);
		
}
