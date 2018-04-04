package com.icss.mvp.service.dts;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.icss.mvp.Constants;
import com.icss.mvp.dao.IDtsTaskDao;
import com.icss.mvp.service.ProjectParameterValueService;
import com.icss.mvp.util.Computor;
import com.icss.mvp.util.DateUtils;

@Service("tr6diCollector")
public class Tr6DICollector implements Computor
{
	@Autowired
	private IDtsTaskDao dtsTaskDao;
	private final static Logger LOG = Logger
	.getLogger(Tr6DICollector.class);
	@Resource
	private ProjectParameterValueService projectParameterValueService;
	/**
	 * DI=10*致命问题数＋3*严重问题数＋1*一般问题数+0.1*提示问题数
	 */
	@Override
	public double compute()
	{
		List<HashMap<String, Object>> resultDatas = dtsTaskDao.queryTr6DI();
		for (HashMap<String, Object> info : resultDatas)
		{
			Date date = null;
			try {
				date = DateUtils.format.parse(String.valueOf(info.get("created_Time")));
			} catch (ParseException e) {
				LOG.error(
						"Tr6DICollector myFormatter.parse fail!",
						e);
			}
			 projectParameterValueService.insertParameterValueByVersion(String.valueOf(info.get("c_Version")), date, 
			    		Constants.TR6DI,String.valueOf(info.get("TR6DI")));
		}
		return 0;
	}
}
