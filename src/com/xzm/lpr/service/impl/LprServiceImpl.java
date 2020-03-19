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
	public List<User> findUser(User user,PageModel pageModel) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		int recordCount = userDao.count(params);
		if(pageModel!=null) {
			pageModel.setRecordCount(recordCount);			
		}
		if(user!=null)
		{
			params.put("user", user);
		}
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		List<User> users = userDao.selectByPage(params);
		 
		return users;
	}
	
	@Override
	public Integer removeUserByLogin(String loginname){
		return userDao.deleteByLogin(loginname);
		
	}
	
	@Override
	public Integer updateUser(User user) {
		return userDao.update(user);
		
	}

	@Override
	public Integer addUser(User user) {
		 return userDao.save(user);
		
	}

	@Transactional(readOnly=true)
	@Override
	public List<Notice> findNotice(PageModel pageModel) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		int recordCount = noticeDao.count(params);
		if(pageModel!=null)
			pageModel.setRecordCount(recordCount);
		
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		
		List<Notice> notices = noticeDao.selectByPage(params);
		 
		return notices;
	}

	@Transactional(readOnly=true)
	@Override
	public Notice findNoticeById(Integer id) {
		return noticeDao.selectById(id);
	}

	@Override
	public Integer removeNoticeById(Integer id) {
		return noticeDao.deleteById(id);	
	}
	
	@Override
	public Integer addNotice(Notice notice) {
		return noticeDao.save(notice);
	}
	
	@Override
	public Integer updateNotice(Notice notice) {
		return noticeDao.update(notice);
		
	}

	@Override
	public List<TraRecord> findTraRecord(User user,PageModel pageModel) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<>();
		int recordCount = traRecordDao.count(params);
		pageModel.setRecordCount(recordCount);
		
		if(!user.getAuthority().equals("系统管理员")) {
			params.put("user", user);
			System.out.println(user.getLicenseplate());
		}
		
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
	public Integer updateTraRecord(TraRecord traRecord) {
		return traRecordDao.update(traRecord);
	}

	@Override
	public List<ParkLot> findParkLot() {
		Map<String,Object> params = new HashMap<>();
		List<ParkLot> parkLots = parkLotDao.selectByPage(params);
		return parkLots;
	}

	@Override
	public Integer updateParkLot(ParkLot parkLot) {
		// TODO Auto-generated method stub
		return parkLotDao.update(parkLot);
	}

	@Override
	public Integer updateParkSpace(ParkSpace parkSpace) {
		return parkSpaceDao.update(parkSpace);
	}

	@Override
	public List<ParkSpace> findParkSpace(User user,String operate,PageModel pageModel) {
		Map<String,Object> params = new HashMap<>();
		
		params.put("user", user);
		params.put("operate", operate);
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
		return parkSpaceDao.deleteById(id);
	}

	@Override
	public List<Notice> findNotice(Notice notice, PageModel pageModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addTraRecord(TraRecord traRecord) {
		return traRecordDao.save(traRecord);
	}

	@Override
	public Integer addParkSpace(ParkSpace parkSpace) {
		return parkSpaceDao.save(parkSpace);
	}

}
