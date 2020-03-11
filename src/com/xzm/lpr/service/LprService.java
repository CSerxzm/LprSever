package com.xzm.lpr.service;

import java.util.List;

import com.xzm.lpr.domain.Notice;
import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.util.tag.PageModel;

public interface LprService {


	User login(String loginname,String password);
	List<User> findUser(PageModel pageModel);
	Integer removeUserByLogin(String loginname);
	Integer updateUser(User user);
	Integer addUser(User user);
	
	List<TraRecord> findTraRecord(PageModel pageModel);
	Integer removeTraRecordById(int id);
	Integer updateTraRecord(TraRecord traRecord);
	
	List<ParkLot> findParkLot();
	Integer updateParkLot(ParkLot parkLot);

	List<ParkSpace> findParkSpace(PageModel pageModel);
	Integer removeParkSpaceById(int id);
	List<ParkSpace> findParkSpace();
	Integer updateParkSpace(ParkSpace parkSpace);
	
	List<Notice> findNotice(Notice notice,PageModel pageModel);
	Notice findNoticeById(Integer id);
	Integer removeNoticeById(Integer id);
	Integer addNotice(Notice notice);
	Integer updateNotice(Notice notice);
	List<Notice> findNotice(PageModel pageModel);
		
}
