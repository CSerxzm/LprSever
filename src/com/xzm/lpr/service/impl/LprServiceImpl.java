package com.xzm.lpr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.xzm.lpr.dao.NoticeDao;
import com.xzm.lpr.dao.ParkLotDao;
import com.xzm.lpr.dao.ParkSpaceDao;
import com.xzm.lpr.dao.TraRecordDao;
import com.xzm.lpr.dao.UserDao;
import com.xzm.lpr.domain.Notice;
import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.service.LprService;
import com.xzm.lpr.util.tag.PageModel;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("lprService")
public class LprServiceImpl implements LprService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TraRecordDao traRecordDao;
	
	@Autowired
	private ParkLotDao parkLotDao;
	
	@Autowired	
	private ParkSpaceDao parkSpaceDao;
	
	@Autowired
	private NoticeDao noticeDao;
	
	@Transactional(readOnly=true)
	@Override
	public User login(String loginname, String password) {
		return userDao.selectByLoginnameAndPassword(loginname, password);
	}

	@Transactional(readOnly=true)
	@Override
	public List<User> findUser(PageModel pageModel) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		int recordCount = userDao.count(params);
		pageModel.setRecordCount(recordCount);
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		List<User> users = userDao.selectByPage(params);
		 
		return users;
	}
	
	@Override
	public void removeUserByLogin(String loginname){
		userDao.deleteByLogin(loginname);
		
	}
	
	@Override
	public Integer modifyUser(User user) {
		return userDao.update(user);
		
	}

	@Override
	public Integer addUser(User user) {
		 return userDao.save(user);
		
	}

	@Transactional(readOnly=true)
	@Override
	public List<Notice> findNotice(Notice notice, PageModel pageModel) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		params.put("notice", notice);
		int recordCount = noticeDao.count(params);
		pageModel.setRecordCount(recordCount);
		
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		
		List<Notice> notices = noticeDao.selectByPage(params);
		 
		return notices;
	}

	/**
	 * HrmService接口findNoticeById方法实现
	 * @see { HrmService }
	 * */
	@Transactional(readOnly=true)
	@Override
	public Notice findNoticeById(Integer id) {
		
		return noticeDao.selectById(id);
	}

	/**
	 * HrmService接口removeNoticeById方法实现
	 * @see { HrmService }
	 * */
	@Override
	public void removeNoticeById(Integer id) {
		noticeDao.deleteById(id);
		
	}
	
	/**
	 * HrmService接口addNotice方法实现
	 * @see { HrmService }
	 * */
	@Override
	public void addNotice(Notice notice) {
		noticeDao.save(notice);
		
	}
	
	/**
	 * HrmService接口modifyNotice方法实现
	 * @see { HrmService }
	 * */
	@Override
	public void modifyNotice(Notice notice) {
		noticeDao.update(notice);
		
	}

	@Override
	public List<TraRecord> findTraRecord(PageModel pageModel) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<>();
		int recordCount = traRecordDao.count(params);
		pageModel.setRecordCount(recordCount);
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		List<TraRecord> traRecord = traRecordDao.selectByPage(params);
		return traRecord;
	}

	@Override
	public Integer removeTraRecordById(int id) {
		return traRecordDao.deleteById(id);
	}

	@Override
	public Integer modifyTraRecord(TraRecord traRecord) {
		return traRecordDao.update(traRecord);
	}

	@Override
	public List<ParkLot> findParkLot() {
		Map<String,Object> params = new HashMap<>();
		List<ParkLot> parkLots = parkLotDao.selectByPage(params);
		return parkLots;
	}

	@Override
	public Integer modifyParkLot(ParkLot parkLot) {
		// TODO Auto-generated method stub
		return parkLotDao.update(parkLot);
	}

	@Override
	public List<ParkSpace> findParkSpace() {
		Map<String,Object> params = new HashMap<>();
		List<ParkSpace> parkSpaces = parkSpaceDao.selectByPage(params);
		return parkSpaces;
	}

	@Override
	public Integer modifyParkSpace(ParkSpace parkSpace) {
		return parkSpaceDao.update(parkSpace);
	}

	@Override
	public List<ParkSpace> findParkSpace(PageModel pageModel) {
		Map<String,Object> params = new HashMap<>();
		int recordCount = parkSpaceDao.count(params);
		pageModel.setRecordCount(recordCount);
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		List<ParkSpace> parkSpaces = parkSpaceDao.selectByPage(params);
		return parkSpaces;
	}

	@Override
	public Integer removeParkSpaceById(int id) {
		// TODO Auto-generated method stub
		return parkSpaceDao.deleteById(id);
	}

}
