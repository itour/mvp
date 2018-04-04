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

@Service("dtsNotPassNumCollector")
public class DtsNotPassNumCollector implements Computor {
	private final static Logger LOG = Logger
			.getLogger(DtsNotPassNumCollector.class);
	@Resource
	private IDtsTaskDao dtsTaskDao;
	@Resource
	private ProjectParameterValueService projectParameterValueService;

	@Override
	public double compute() {
		List<HashMap<String, Object>> datas=dtsTaskDao.query();
		for (HashMap<String, Object> info : datas) {
			Date date = null;
			try {
				date = DateUtils.format.parse(String.valueOf(info
						.get("created_Time")));
			} catch (ParseException e) {
				LOG.error(
						"IterationDefectDensityCollector myFormatter.parse fail!",e);
			}
			 projectParameterValueService.insertParameterValueByVersion(String.valueOf(info.get("c_Version")), date, 
			    		Constants.DTS_NO_PASS_NUM,String.valueOf(info.get("nopassNum")));
		}
		return 0;
	}

}
