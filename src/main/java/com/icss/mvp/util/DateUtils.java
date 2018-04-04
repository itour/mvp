package com.icss.mvp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils
{
    
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    
    public static final SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    
    public static final SimpleDateFormat format2 = new SimpleDateFormat("yyyyMM");
    
    private DateUtils()
    {
    }
    
    /**
     * 获得系统当前日期，并以YYYYMM格式返回
     * 
     * @return yyyyMM
     */
    public static String getSystemMonth()
    {
        Calendar c = Calendar.getInstance();
        String year = c.get(Calendar.YEAR) + "";
        String month = c.get(Calendar.MONTH) + 1 + "";
        if (month.length() == 1)
        {
            month = "0" + month;
        }
        String reckonMonth = year + month;
        return reckonMonth;
    }
    
    /**
     * 获得系统当前月前后n月，并以YYYYMM格式返回
     * @param n 1=下月 -1=上月
     * 
     * @return yyyyMM
     */
    public static String getSystemFewMonth(int n)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, n);
        String year = c.get(Calendar.YEAR) + "";
        String month = c.get(Calendar.MONTH) + 1 + "";
        if (month.length() == 1)
        {
            month = "0" + month;
        }
        String reckonMonth = year + month;
        return reckonMonth;
    }
    
    /**
     * 获得系统当前日期，并以YYYYMM格式返回
     * 
     * @return yyyyMM
     */
    public static String getMonth()
    {
        Calendar c = Calendar.getInstance();
        String year = c.get(Calendar.YEAR) + "";
        String month = c.get(Calendar.MONTH) + 1 + "";
        if (month.length() == 1)
        {
            month = "0" + month;
        }
        String reckonMonth = year + "-" + month;
        return reckonMonth;
    }
    
}
