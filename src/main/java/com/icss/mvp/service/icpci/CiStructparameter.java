package com.icss.mvp.service.icpci;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.icss.mvp.Constants;
import com.icss.mvp.dao.IIcpCITaskDao;
import com.icss.mvp.service.ProjectParameterValueService;
import com.icss.mvp.util.Computor;

@Service("ciStructparameter")
public class CiStructparameter implements Computor{
	@Resource
	private ProjectParameterValueService projectParameterValueService;
	@Resource
	private IIcpCITaskDao iIcpCITaskDao;
	
	@Override
	public double compute() {
		List<HashMap<String, Object>> resultDatas =iIcpCITaskDao.queryStructPara();
		projectParameterValueService.inserCiParameter(resultDatas, "avgStruTime", Constants.CI_AVG_STRCUT);
		projectParameterValueService.inserCiParameter(resultDatas, "versionRate", Constants.CI_STRCUT_RATE);
		return 0;
	}
}
