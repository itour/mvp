package com.icss.mvp.entity;

import java.util.Date;

public class ProjectInfo
{
	private String no;

	private String name;

	private String bu;

	private String du;

	private String area;

	private String pm;

	private String type;

	private Date startDate;

	private Date endDate;

	private Date importDate;

	private String status;

	private String projectType;

	private String qa;

	private String month;

	private String pdu;

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public String getNo()
	{
		return no;
	}

	public void setNo(String no)
	{
		this.no = no;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getBu()
	{
		return bu;
	}

	public void setBu(String bu)
	{
		this.bu = bu;
	}

	public String getDu()
	{
		return du;
	}

	public void setDu(String du)
	{
		this.du = du;
	}

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

	public String getPm()
	{
		return pm;
	}

	public void setPm(String pm)
	{
		this.pm = pm;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getPdu()
	{
		return pdu;
	}

	public void setPdu(String pdu)
	{
		this.pdu = pdu;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public Date getImportDate()
	{
		return importDate;
	}

	public void setImportDate(Date importDate)
	{
		this.importDate = importDate;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getProjectType()
	{
		return projectType;
	}

	public void setProjectType(String projectType)
	{
		this.projectType = projectType;
	}

	public String getQa()
	{
		return qa;
	}

	public void setQa(String qa)
	{
		this.qa = qa;
	}

}
