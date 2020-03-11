package com.xzm.lpr.domain;

import java.io.Serializable;

public class Notice implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;	
	private String title;
	private String content;
	private String create_date;
	private String name_publish;

	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Notice(String title,String content,String create_date,String name_publish) {
		this.title=title;
		this.content=content;
		this.create_date=create_date;
		this.name_publish=name_publish;
	}
	
	public Notice(String title,String content,String name_publish) {
		this.title=title;
		this.content=content;
		this.name_publish=name_publish;
	}

	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return this.title;
	}
	public void setContent(String content){
		this.content = content;
	}
	public String getContent(){
		return this.content;
	}
	public void setCreate_date(String create_date){
		this.create_date = create_date;
	}
	public String getCreate_date(){
		if(create_date==null)
			return create_date;
		return this.create_date.split("[.]")[0];
	}
	
	public String getName_publish() {
		return name_publish;
	}

	public void setName_publish(String name_publish) {
		this.name_publish = name_publish;
	}
}