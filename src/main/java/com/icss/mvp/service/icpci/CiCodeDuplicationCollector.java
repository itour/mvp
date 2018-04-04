package com.icss.mvp.service.icpci;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.icss.mvp.Constants;
import com.icss.mvp.dao.IIcpCITaskDao;
import com.icss.mvp.service.ProjectParameterValueService;
import com.icss.mvp.util.DateUtils;

@Service("ciCodeDuplication")
public class CiCodeDuplicationCollector {
	private final static Logger LOG = Logger
	.getLogger(CiCodeDuplicationCollector.class);
	@Resource
	private ProjectParameterValueService projectParameterValueService;
	
	@Resource
	private IIcpCITaskDao iIcpCITaskDao;
	
    
	
	public double computeCodeDuplication(){
		List<HashMap<String, Object>> resultDatas =iIcpCITaskDao.querySimian();
		for(HashMap<String, Object> info:resultDatas){
			Date date = null;
			try {
				date =DateUtils.format.parse(String.valueOf(info
						.get("struct_time")));
			} catch (ParseException e) {
				LOG.error("CodeDuplication myFormatter.parse fail!",e);
			}
			double value=Double.parseDouble(String.valueOf(info.get("value")));
			projectParameterValueService.insertParameterValue(String.valueOf(info.get("no")), date, 
				Integer.valueOf(Constants.CODE_DUPLICATION),value);
		}
		
		return 0;
	}
}
