package com.xzm.lpr.provider;

import static com.xzm.lpr.util.common.LprConstants.NOTICETABLE;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

import com.xzm.lpr.domain.Notice;

public class NoticeDynaSqlProvider {

	public String selectWhitParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(NOTICETABLE);
				if(params.get("notice") != null){
					Notice notice = (Notice)params.get("notice");
					if(notice.getTitle() != null && !notice.getTitle().equals("")){
						WHERE("  title LIKE CONCAT ('%',#{notice.title},'%') ");
					}
					if(notice.getContent() != null && !notice.getContent().equals("")){
						WHERE("  content LIKE CONCAT ('%',#{notice.content},'%') ");
					}
				}
			}
		}.toString();
		
		sql+=" ORDER BY create_date DESC" ;
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}	
	// 动态查询总数量
	public String count(Map<String, Object> params){
		return new SQL(){
			{
				SELECT("count(*)");
				FROM(NOTICETABLE);
				if(params.get("notice") != null){
					Notice notice = (Notice)params.get("notice");
					if(notice.getTitle() != null && !notice.getTitle().equals("")){
						WHERE("  title LIKE CONCAT ('%',#{notice.title},'%') ");
					}
					if(notice.getContent() != null && !notice.getContent().equals("")){
						WHERE("  content LIKE CONCAT ('%',#{notice.content},'%') ");
					}
				}
			}
		}.toString();
	}	
	// 动态插入
	public String insertNotice(Notice notice){
		
		return new SQL(){
			{
				INSERT_INTO(NOTICETABLE);
				if(notice.getTitle() != null && !notice.getTitle().equals("")){
					VALUES("title", "#{title}");
				}
				if(notice.getContent() != null && !notice.getContent().equals("")){
					VALUES("content", "#{content}");
				}
				if(notice.getCreate_date() != null && !notice.getCreate_date().equals("")){
					VALUES("create_date", "#{create_date}");
				}
				if(notice.getName_publish() != null && !notice.getName_publish().equals("")){
					VALUES("name_publish", "#{name_publish}");
				}
			}
		}.toString();
	}
	// 动态更新
	public String updateNotice(Notice notice){
		
		return new SQL(){
			{
				UPDATE(NOTICETABLE);
				if(notice.getTitle() != null && !notice.getTitle().equals("")){
					SET(" title = #{title} ");
				}
				if(notice.getContent() != null && !notice.getContent().equals("")){
					SET(" content = #{content} ");
				}
				if(notice.getCreate_date() != null && !notice.getCreate_date().equals("")){
					SET(" create_date = #{create_date} ");
				}
				if(notice.getName_publish() != null && !notice.getName_publish().equals("")){
					SET(" name_publish = #{name_publish} ");
				}
				WHERE(" id = #{id} ");
			}
		}.toString();
	}
	
}
