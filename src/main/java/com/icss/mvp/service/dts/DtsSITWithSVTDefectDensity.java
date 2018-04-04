package com.icss.mvp.service.dts;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icss.mvp.Constants;
import com.icss.mvp.dao.IDtsTaskDao;
import com.icss.mvp.dao.IProjectListDao;
import com.icss.mvp.dao.IProjectParameterValueDao;
import com.icss.mvp.entity.ProjectInfo;
import com.icss.mvp.entity.ProjectParameterValue;
import com.icss.mvp.service.ProjectParameterValueService;
import com.icss.mvp.util.DateUtils;

@Service("dtsSITWithSVTDefectDensity")
public class DtsSITWithSVTDefectDensity {

	public static List<HashMap<String, Object>> datas;

	@Autowired
	private IDtsTaskDao dtsTaskDao;
	@Autowired
	private IProjectParameterValueDao iProjectParameterValueDao;
	@Resource
	private ProjectParameterValueService projectParameterValueService;
	@Resource
	private IProjectListDao projectListDao;

	
	public double collectDensityNum() {
		datas = dtsTaskDao.queryDensity();
		for (HashMap<String, Object> info : datas) {
			String version = String.valueOf(info.get("c_Version"));
		    String id=Constants.SVI_DEFECT_DENSITY;
		    String month=String.valueOf(info.get("month"));
		    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		    Date date = null;
			try {
				date = format.parse(month);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Double amountNum = Double.parseDouble(String.valueOf(info
					.get("amountNum")));		    
		    
		    List<ProjectInfo> projectInfos = projectListDao.isExistVersion(version);
			if (projectInfos.size() > 0){
				double value = 0;
				for (ProjectInfo projectInfo : projectInfos){
					String no = projectInfo.getNo();
					double codeAddNum =  projectParameterValueService.getParameterValue(no, date, Constants.CODE_ADD_NUM);
					if (codeAddNum > 0){
						value= amountNum / codeAddNum;
					}
					projectParameterValueService.insertParameterValue(no, date, Integer.parseInt(id) , value);
				}
			}
		}
		return 0;
	}
	
}
