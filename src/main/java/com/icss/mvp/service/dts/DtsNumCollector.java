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

@Service("dtsNumCollector")
public class DtsNumCollector implements Computor {

	@Resource
	private IDtsTaskDao dtsTaskDao;
	private final static Logger LOG = Logger.getLogger(DtsNumCollector.class);
	@Resource
	private ProjectParameterValueService projectParameterValueService;
	@Override
	public double compute() {
		List<HashMap<String, Object>> datas=dtsTaskDao.query();
		Date date = null;
		for (HashMap<String, Object> info : datas) {
			try {
				date = DateUtils.format.parse(String.valueOf(info.get("created_Time")));
			} catch (ParseException e) {
				LOG.error("DtsNumCollector myFormatter.parse fail!", e);
			}
		    projectParameterValueService.insertParameterValueByVersion(String.valueOf(info.get("c_Version")), date, 
		    		Constants.TOTAL_DTS_NUM,String.valueOf(info.get("dtsNum")));
		}
		return 0;
	}

}
