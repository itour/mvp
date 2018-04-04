package com.icss.mvp.entity;

import java.util.Date;

/**
 * 
 * <pre>
 * <b>描述：用户实体</b>
 * <b>任务编号：</b>
 * <b>作者：张鹏飞</b> 
 * <b>创建日期： 2017年5月12日 下午2:57:58</b>
 * </pre>
 */
public class UserInfo
{
    private String USERID;
    
    private String USERCODE;
    
    private String USERNAME;
    
    private String PASSWORD;
    
    private int ISONLINE;
    
    private int ISDEL;
    
    private String CREATER;
    
    private String CREATERNAME;
    
    private Date CREATERTIME;
    
    private String UPDATER;
    
    private String UPDATERNAME;
    
    private Date UPDATERTIME;
    
    private int IDENTITY;// 用户身份 1=root 2=admin 3=user
    
	public String getUSERID()
    {
        return USERID;
    }
    
    public void setUSERID(String uSERID)
    {
        USERID = uSERID;
    }
    
    public String getUSERCODE()
    {
        return USERCODE;
    }
    
    public void setUSERCODE(String uSERCODE)
    {
        USERCODE = uSERCODE;
    }
    
    public String getUSERNAME()
    {
        return USERNAME;
    }
    
    public void setUSERNAME(String uSERNAME)
    {
        USERNAME = uSERNAME;
    }
    
    public String getPASSWORD()
    {
        return PASSWORD;
    }
    
    public void setPASSWORD(String pASSWORD)
    {
        PASSWORD = pASSWORD;
    }
    
    public int getISONLINE()
    {
        return ISONLINE;
    }
    
    public void setISONLINE(int iSONLINE)
    {
        ISONLINE = iSONLINE;
    }
    
    public int getISDEL()
    {
        return ISDEL;
    }
    
    public void setISDEL(int iSDEL)
    {
        ISDEL = iSDEL;
    }
    
    public String getCREATER()
    {
        return CREATER;
    }
    
    public void setCREATER(String cREATER)
    {
        CREATER = cREATER;
    }
    
    public String getCREATERNAME()
    {
        return CREATERNAME;
    }
    
    public void setCREATERNAME(String cREATERNAME)
    {
        CREATERNAME = cREATERNAME;
    }
    
    public Date getCREATERTIME()
    {
        return CREATERTIME;
    }
    
    public void setCREATERTIME(Date cREATERTIME)
    {
        CREATERTIME = cREATERTIME;
    }
    
    public String getUPDATER()
    {
        return UPDATER;
    }
    
    public void setUPDATER(String uPDATER)
    {
        UPDATER = uPDATER;
    }
    
    public String getUPDATERNAME()
    {
        return UPDATERNAME;
    }
    
    public void setUPDATERNAME(String uPDATERNAME)
    {
        UPDATERNAME = uPDATERNAME;
    }
    
    public Date getUPDATERTIME()
    {
        return UPDATERTIME;
    }
    
    public void setUPDATERTIME(Date uPDATERTIME)
    {
        UPDATERTIME = uPDATERTIME;
    }
    
    public int getIDENTITY()
    {
        return IDENTITY;
    }
    
    public void setIDENTITY(int iDENTITY)
    {
        IDENTITY = iDENTITY;
    }
    
}
