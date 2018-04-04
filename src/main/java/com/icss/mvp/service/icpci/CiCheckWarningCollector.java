package com.icss.mvp.service.icpci;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icss.mvp.Constants;
import com.icss.mvp.dao.IIcpCITaskDao;
import com.icss.mvp.service.ProjectParameterValueService;
import com.icss.mvp.util.DateUtils;

@Service("ciCheckWarning")
public class CiCheckWarningCollector {
	private final static Logger LOG = Logger
	.getLogger(CiCheckWarningCollector.class);
	@Resource
	private ProjectParameterValueService projectParameterValueService;
	@Resource
	private IIcpCITaskDao iIcpCITaskDao;
	
	public double computeBugs(){
		List<HashMap<String, Object>> resultDatas =iIcpCITaskDao.queryBugs();
		for(HashMap<String, Object> info:resultDatas){
			Date date = null;
			try {
				date = DateUtils.format.parse(String.valueOf(info
						.get("struct_time")));
			} catch (ParseException e) {
				LOG.error("FindBugs myFormatter.parse fail!",e);
			}
			double value=Double.parseDouble(String.valueOf(info.get("value")));
			projectParameterValueService.insertParameterValue(String.valueOf(info.get("no")), date, 
				Integer.valueOf(Constants.STATIC_CHECK_WARING),value);
		}
		
		return 0;
	}
}
