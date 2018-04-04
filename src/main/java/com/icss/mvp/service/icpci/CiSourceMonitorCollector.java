package com.icss.mvp.service.icpci;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.icss.mvp.Constants;
import com.icss.mvp.dao.IIcpCITaskDao;
import com.icss.mvp.service.ProjectParameterValueService;
import com.icss.mvp.util.Computor;

@Service("ciSourceMonitorCollector")
public class CiSourceMonitorCollector implements Computor {
	
	@Resource
	private IIcpCITaskDao iIcpCITaskDao;
	@Resource
	private ProjectParameterValueService projectParameterValueService;

	@Override
	public double compute() {
		List<HashMap<String, Object>> resultDatas = iIcpCITaskDao.querySourceMonitorPara();
		projectParameterValueService.inserCiParameter(resultDatas, "avg_complexity_statement", Constants.CI_SOURCE_MONITOR);
		return 0;
	}

}
