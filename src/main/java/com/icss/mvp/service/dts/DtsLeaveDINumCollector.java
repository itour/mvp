package com.icss.mvp.service.dts;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icss.mvp.Constants;
import com.icss.mvp.dao.IDtsTaskDao;
import com.icss.mvp.service.ProjectParameterValueService;
import com.icss.mvp.util.Computor;
import com.icss.mvp.util.DateUtils;

@Service("dtsLeaveDINumCollector")
public class DtsLeaveDINumCollector implements Computor
{
	@Resource
	private IDtsTaskDao dtsTaskDao;
	private final static Logger LOG = Logger.getLogger(DtsLeaveDINumCollector.class);
	@Resource
	private ProjectParameterValueService projectParameterValueService;

	@Override
	public double compute()
	{
		List<HashMap<String, Object>> resultDatas = dtsTaskDao.queryDI();
		for (HashMap<String, Object> info : resultDatas)
		{
			Date date =null;
			try {
			    date = DateUtils.format.parse(String.valueOf(info.get("created_Time")));
			} catch (ParseException e) {
				LOG.error("DtsLeaveDINumCollector myFormatter.parse fail!" , e);
			}
		    projectParameterValueService.insertParameterValueByVersion(String.valueOf(info.get("c_Version")), date, 
		    		Constants.LEAVE_DI,String.valueOf(info.get("dtsLeaveDINum")));
			
		}
		return 0;
	}

}
