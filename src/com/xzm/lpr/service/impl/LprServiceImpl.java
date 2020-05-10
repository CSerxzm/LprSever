package com.xzm.lpr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
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
import com.xzm.lpr.dao.WalletRecordDao;
import com.xzm.lpr.domain.Notice;
import com.xzm.lpr.domain.ParkLot;
import com.xzm.lpr.domain.ParkSpace;
import com.xzm.lpr.domain.TraRecord;
import com.xzm.lpr.domain.User;
import com.xzm.lpr.domain.WalletRecord;
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
	
	
	@Autowired
	private WalletRecordDao walletRecordDao;	
	
	@Transactional(readOnly=true)
	@Override
	public User login(String loginname, String password) {
		return userDao.selectByLoginnameAndPassword(loginname, password);
	}

	@Transactional(readOnly=true)
	@Override
	public List<User> findUser(User user,PageModel pageModel,String keyword) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		params.put("keyword", keyword);
		if(user!=null)
		{
			params.put("user", user);
		}
		int recordCount = userDao.count(params);
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		if(pageModel!=null) {
			pageModel.setRecordCount(recordCount);			
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
	public List<Notice> findNotice(PageModel pageModel,String keyword) {
		/** 当前需要分页的总数据条数  */
		Map<String,Object> params = new HashMap<>();
		params.put("keyword", keyword);
		
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
	public List<TraRecord> findTraRecord(User user,PageModel pageModel,String keyword) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<>();
		
		if(user!=null && user.getAuthority()!=null && user.getAuthority().equals("user") ) {
			params.put("user", user);
			System.out.println(user.getLicenseplate());
		}
		params.put("keyword", keyword);
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
	
	public Integer getParkLotActivitycost_per() {
		Map<String,Object> params = new HashMap<>();
		List<ParkLot> parkLots = parkLotDao.selectByPage(params);
		if(parkLots.size()>0) {
			return parkLots.get(0).getActivitycost_per();
		}
		return null;
	}

	@Override
	public Integer updateParkSpace(ParkSpace parkSpace) {
		return parkSpaceDao.update(parkSpace);
	}

	@Override
	public List<ParkSpace> findParkSpace(User user,String operate,PageModel pageModel,String keyword) {
		Map<String,Object> params = new HashMap<>();
		
		params.put("user", user);
		params.put("operate", operate);
		params.put("keyword", keyword);
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
	public Integer addTraRecord(TraRecord traRecord) {
		return traRecordDao.save(traRecord);
	}

	@Override
	public Integer addParkSpace(ParkSpace parkSpace) {
		return parkSpaceDao.save(parkSpace);
	}

	@Override
	public Integer addWalletRecord(WalletRecord walletRecord) {
		
		String operation = walletRecord.getOperation();
		String loginname = walletRecord.getName();
		Integer cost = walletRecord.getCost();
		
		System.out.println(operation+"/"+loginname+"/"+cost);
		
		if(operation.equals("充值")||operation.equals("收入")) {
			walletRecordDao.save(walletRecord);
			return userDao.addWallet(loginname,cost);
		}else {
			Integer money = userDao.getWallet(loginname);
			if (money>=cost) {
				walletRecordDao.save(walletRecord);
				return userDao.subWallet(loginname,cost);				
			}else {
				return null;
			}

		}
	}

	@Override
	public List<WalletRecord> findWalletRecord(User user, PageModel pageModel,String keyword) {
		
		Map<String,Object> params = new HashMap<>();
		
		if(user!=null && user.getAuthority()!=null && user.getAuthority().equals("user") ) {
			params.put("user", user);
			System.out.println(user.getLicenseplate());
		}
		params.put("keyword", keyword);
		
		int recordCount = walletRecordDao.count(params);
		pageModel.setRecordCount(recordCount);
		
		if(recordCount > 0){
	        /** 开始分页查询数据：查询第几页的数据 */
		    params.put("pageModel", pageModel);
	    }
		List<WalletRecord> walletRecord = walletRecordDao.selectByPage(params);
		return walletRecord;
	}

	/*
	 租赁停车场，对用户表、费用表、车位表进行改变。
	 */
	@Override
	public Integer rentParkSpace(ParkSpace parkSpace, User user) {
		
		String loginname = user.getLoginname();
		Integer wallet = userDao.getWallet(loginname);
		Integer money = user.getWallet();
		
		/*
		 * 判断是否已经租赁
		 */
		Integer parkspace_id = userDao.getParkspace_id(loginname);
		if(parkspace_id != null) {
			return 2;//表示已经有租赁
		}
		/*
		 * 判断余额是否足够
		 */
		if(wallet>=money) {
			wallet = wallet-money;
			user.setWallet(wallet);
			Integer i = parkSpaceDao.update(parkSpace);
			Integer j = userDao.update(user);
			WalletRecord walletRecord = new WalletRecord(loginname,"支出",money);
			Integer k = walletRecordDao.save(walletRecord);
			return (i&j&k);
		}
		return null;
	}

	/*
	 * 通行记录操作
	 */
	@Override
	public ParkSpace getParkSpaceOne() {
		
		List<ParkSpace> parkSpaces = parkSpaceDao.selectState();
		if(parkSpaces.size()>0) {
			return parkSpaces.get(0);
		}
		else
			return null;
	}

	/*
	 * 判断该车牌是否还有用户绑定，
	 * 	若有绑定，返回停车场号，若没有绑定，返回空
	 */
	@Override
	public Integer getParkSpace_idByLicenseplate(String licenseplate) {
		// TODO Auto-generated method stub
		User  user = userDao.getUserByLicenseplate(licenseplate);
		if(user != null) {
			Integer parkspace_id = user.getParkspace_id();
			return parkspace_id;
		}
		return null;
	}

	/*
	 * 查找停车时间的停车场通行记录，通过车牌号码进行查找，返回该记录。
	 */
	@Override
	public TraRecord getTraRecordDate_out(String licenseplate) {
		// TODO Auto-generated method stub
		return traRecordDao.getTraRecordDate_out(licenseplate);
	}
	
}
