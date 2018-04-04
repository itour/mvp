package com.icss.mvp.entity;

import java.util.Date;

public class ProjectDetailInfo {
	private String no;

	private String name;

	private String bu;

	private String pdu;

	private String du;

	private String area;

	private String type;

	private Date startDate;

	private Date endDate;

	private Double predictMoney;

	private Double workerCount;

	private String projectType;
	private String po;
	private Double countOfDay;
	private Double countOfMonth;

	private String version;
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public Double getCountOfDay() {
		return countOfDay;
	}

	public void setCountOfDay(Double countOfDay) {
		this.countOfDay = countOfDay;
	}

	public Double getCountOfMonth() {
		return countOfMonth;
	}

	public void setCountOfMonth(Double countOfMonth) {
		this.countOfMonth = countOfMonth;
	}

	private Date importDate;

	private String importUser;

	public void setNo(String no) {
		this.no = no;
	}

	public String getNo() {
		return no;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getBu() {
		return bu;
	}

	public void setPdu(String pdu) {
		this.pdu = pdu;
	}

	public String getPdu() {
		return pdu;
	}

	public void setDu(String du) {
		this.du = du;
	}

	public String getDu() {
		return du;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setPredictMoney(Double predictMoney) {
		this.predictMoney = predictMoney;
	}

	public Double getPredictMoney() {
		return predictMoney;
	}

	public void setWorkerCount(Double workerCount) {
		this.workerCount = workerCount;
	}

	public Double getWorkerCount() {
		return workerCount;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportUser(String importUser) {
		this.importUser = importUser;
	}

	public String getImportUser() {
		return importUser;
	}
}
