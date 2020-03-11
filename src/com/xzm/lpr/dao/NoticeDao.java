package com.xzm.lpr.dao;

import static com.xzm.lpr.util.common.LprConstants.NOTICETABLE;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.*;

import com.xzm.lpr.domain.Notice;
import com.xzm.lpr.provider.NoticeDynaSqlProvider;

public interface NoticeDao {

	@SelectProvider(type=NoticeDynaSqlProvider.class,method="selectWhitParam")
	List<Notice> selectByPage(Map<String, Object> params);
	
	@SelectProvider(type=NoticeDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);
		
	@Select("select * from "+NOTICETABLE+" where ID = #{id}")
	Notice selectById(int id);
	
	@Delete(" delete from "+NOTICETABLE+" where id = #{id} ")
	Integer deleteById(Integer id);
		
	@InsertProvider(type=NoticeDynaSqlProvider.class,method="insertNotice")
	Integer save(Notice notice);
		
	@UpdateProvider(type=NoticeDynaSqlProvider.class,method="updateNotice")
	Integer update(Notice notice);
	
}
