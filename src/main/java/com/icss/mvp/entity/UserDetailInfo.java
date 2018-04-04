package com.icss.mvp.entity;

import java.util.Date;

public class UserDetailInfo {
	private String USERID;
	
	private String USERNAME;
    
    private String PASSWORD;
    
    private String REPASSWORD;
    
    private String CREATER;
    
    private Date CREATETIME;
    
    private String UPDATER;
    
    private Date UPDATETIME;
    
    private String IDENTITY;

    private String PARMA;
    
    public String getUserId() {
		return USERID;
	}

	public void setUserId(String userId) {
		this.USERID = userId;
	}
    
	public String getIdentity() {
		return IDENTITY;
	}

	public void setIdentity(String identity) {
		this.IDENTITY = identity;
	}

	public String getUserName() {
		return USERNAME;
	}

	public void setUserName(String userName) {
		this.USERNAME = userName;
	}

	public String getPassword() {
		return PASSWORD;
	}

	public void setPassword(String password) {
		this.PASSWORD = password;
	}

	public String getRepassword() {
		return REPASSWORD;
	}

	public void setRepassword(String repassword) {
		this.REPASSWORD = repassword;
	}

	public String getCreater() {
		return CREATER;
	}

	public void setCreater(String creater) {
		this.CREATER = creater;
	}

	public Date getCreateData() {
		return CREATETIME;
	}

	public void setCreateData(Date createData) {
		this.CREATETIME = createData;
	}

	public String getUpdater() {
		return UPDATER;
	}

	public void setUpdater(String updater) {
		this.UPDATER = updater;
	}

	public Date getUpdataData() {
		return UPDATETIME;
	}

	public void setUpdataData(Date updataData) {
		this.UPDATETIME = updataData;
	}

	public String getParma(){
		return PARMA;
	}
	
	public void setParma(String parma) {
		this.PARMA = parma;
	}   
}