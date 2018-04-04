package com.icss.mvp.service.icpci;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.icss.mvp.Constants;
import com.icss.mvp.dao.IIcpCITaskDao;
import com.icss.mvp.service.ProjectParameterValueService;
import com.icss.mvp.util.Computor;

@Service("ciCodeDexCollector")
public class CiCodeDexCollector implements Computor {

	@Resource
	private ProjectParameterValueService projectParameterValueService;
	@Resource
	private IIcpCITaskDao iIcpCITaskDao;

	@Override
	public double compute() {
		List<HashMap<String, Object>> resultDatas = iIcpCITaskDao.queryCodeDexPara();
		projectParameterValueService.inserCiParameter(resultDatas, "codeCCNum", Constants.CI_CODE_CC);
		return 0;
	}

}
