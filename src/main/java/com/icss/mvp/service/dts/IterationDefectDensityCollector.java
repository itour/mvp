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
import com.icss.mvp.dao.IProjectListDao;
import com.icss.mvp.entity.ProjectInfo;
import com.icss.mvp.service.ProjectParameterValueService;
import com.icss.mvp.util.Computor;
import com.icss.mvp.util.DateUtils;

@Service("iterationDefectDensityCollector")
public class IterationDefectDensityCollector implements Computor {

	@Resource
	private IProjectListDao projectListDao;
	@Resource
	private IDtsTaskDao dtsTaskDao;
	@Resource
	private ProjectParameterValueService projectParameterValueService;
	
	private final static Logger LOG = Logger
			.getLogger(IterationDefectDensityCollector.class);

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
						"IterationDefectDensityCollector myFormatter.parse fail!",
						e);
			}
			
			List<ProjectInfo> projectInfos = projectListDao.isExistVersion(String.valueOf(info.get("c_Version")));
			if (projectInfos.size() > 0){
				for (ProjectInfo projectInfo : projectInfos){
					String no = projectInfo.getNo();
					double dtsNumList = projectParameterValueService.getParameterValue(no,date,Constants.TOTAL_DTS_NUM);
					double codeAddsSum = projectParameterValueService.getParameterValue(no,date,Constants.CODE_ADD_NUM);
					double defect = 0;
					if (codeAddsSum != 0 ) {
						defect = dtsNumList	/ codeAddsSum;
					}
					projectParameterValueService.insertParameterValue(no, date,Integer.valueOf(Constants.ITERATION_DEFECT_DENSITY) , defect);
				}
			}

		}
		return 0;
	}

}
