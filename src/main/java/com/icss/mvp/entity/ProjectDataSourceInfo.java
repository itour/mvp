package com.icss.mvp.entity;

import java.util.Date;

public class ProjectDataSourceInfo {
	
	private String no;
	
	//private String sourceValue;
	private String source_value;
	private String url;
	
	private String user;
	
	private String password;
	
	private Date createDate;
	
	private String creator;
	
	private Date updateDate;
	
	private String updateUser;
	
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNo() {
		return no;
	}

	/*public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}

	public String getSourceValue() {
		return sourceValue;
	}*/
	

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSource_value() {
		return source_value;
	}

	public void setSource_value(String source_value) {
		this.source_value = source_value;
	}

	public String getUrl() {
		return url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

}
