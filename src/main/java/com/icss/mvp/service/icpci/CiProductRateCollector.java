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
import com.icss.mvp.dao.IProjectListDao;
import com.icss.mvp.entity.ProjectDetailInfo;
import com.icss.mvp.service.ProjectParameterValueService;
import com.icss.mvp.util.DateUtils;

@Service("ciProductRate")
public class CiProductRateCollector {
	private final static Logger LOG = Logger
	.getLogger(CiProductRateCollector.class);
	@Resource
	private ProjectParameterValueService projectParameterValueService;
	@Resource
	private IIcpCITaskDao iIcpCITaskDao;
	@Resource
	private IProjectListDao iprojDao;

	
	public double computeProductRate(){
		List<HashMap<String, Object>> resultDatas =iIcpCITaskDao.queryCCT();
		for(HashMap<String, Object> info:resultDatas){
			Date date = null;
			try {
				date = DateUtils.format.parse(String.valueOf(info
						.get("struct_time")));
			} catch (ParseException e) {
				LOG.error("ProductRate myFormatter.parse fail!",e);
			}
			double sumValue=Double.parseDouble(String.valueOf(info.get("value")));
			List<ProjectDetailInfo> proInfos=iprojDao.queryProjInfo(String.valueOf(info.get("no")));
			 if(proInfos.size()>0){
				 double value = 0;
		    	  for(ProjectDetailInfo projInfo: proInfos){
		    		double projWorkerCount=projInfo.getCountOfDay();
		    		if(projWorkerCount>0){
		   			 value=sumValue/projWorkerCount;
		   			}
		    		 projectParameterValueService.insertParameterValue(String.valueOf(info.get("no")), date, 
		    			Integer.valueOf(Constants.MONTH_PRODUCT_RATE),value);
		    		 projectParameterValueService.insertParameterValue(String.valueOf(info.get("no")), date, 
		    				Integer.valueOf(Constants.PROJECT_E2E_PRODUCT_RATE),value);
		    		 projectParameterValueService.insertParameterValue(String.valueOf(info.get("no")), date, 
		    			Integer.valueOf(Constants.ITERATION_PRODUCT_RATE),value);	      
		    	  }
		      }  
		}
		
		return 0;
	}
	
}