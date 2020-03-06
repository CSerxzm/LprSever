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
	void removeUserByLogin(String loginname);
	Integer modifyUser(User user);
	Integer addUser(User user);
	
	List<TraRecord> findTraRecord(PageModel pageModel);
	Integer removeTraRecordById(int id);
	Integer modifyTraRecord(TraRecord traRecord);
	
	List<ParkLot> findParkLot();
	Integer modifyParkLot(ParkLot parkLot);

	List<ParkSpace> findParkSpace(PageModel pageModel);
	Integer removeParkSpaceById(int id);
	List<ParkSpace> findParkSpace();
	Integer modifyParkSpace(ParkSpace parkSpace);
	
	List<Notice> findNotice(Notice notice,PageModel pageModel);
	Notice findNoticeById(Integer id);
	public void removeNoticeById(Integer id);
	void addNotice(Notice notice);
	void modifyNotice(Notice notice);
	
	
	
}
