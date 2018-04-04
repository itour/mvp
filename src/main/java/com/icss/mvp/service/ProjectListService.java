package com.icss.mvp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.icss.mvp.dao.ICodeMasterInfoDao;
import com.icss.mvp.dao.IOrgnizeInfoDao;
import com.icss.mvp.dao.IParameterInfo;
import com.icss.mvp.dao.IProjectListDao;
import com.icss.mvp.dao.IProjectParameter;
import com.icss.mvp.entity.CodeMasterInfo;
import com.icss.mvp.entity.OrganizeInfo;
import com.icss.mvp.entity.Page;
import com.icss.mvp.entity.ParameterInfo;
import com.icss.mvp.entity.ProjectDetailInfo;
import com.icss.mvp.entity.ProjectInfo;
import com.icss.mvp.entity.ProjectManager;
import com.icss.mvp.entity.ProjectParameter;

@Service("projectListService")
@Transactional
public class ProjectListService {
	@Resource
	private IProjectListDao projectListDao;
	@Resource
	private IProjectParameter projParam;
	@Resource
	private IOrgnizeInfoDao orgInfoDao;
	@Resource
	private IProjectParameter projectParameter;
	@Resource
	private IParameterInfo parameterInfo;
	@Resource
	private ICodeMasterInfoDao codeMasterInfo;

	private final static String[] HEADERS = { "项目名称", "项目编号", "事业部", "交付部","产品",
			"地域", "项目经理", "项目类型", "项目开始日期", "项目结束日期", "项目导入日期", "状态" };
	private final static Logger LOG = Logger
			.getLogger(ProjectListService.class);

	public Map<String, Object> getList(ProjectInfo proj, Page page) {
		List<ProjectInfo> projList = projectListDao.getList(proj,
				page.getSort(), page.getOrder());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", projList);
		result.put("total", projList.size());
		return result;
	}

	public byte[] exportExcel(ProjectInfo proj) {
		List<ProjectInfo> projList = projectListDao.getList(proj,
				"", "");
		proj = new ProjectInfo();
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow(0);
		Cell cell;
		CellStyle cellStyle = wb.createCellStyle();
		CreationHelper creationHelper = wb.getCreationHelper();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(
				"yyyy-MM-dd"));
		for (int i = 0; i < HEADERS.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(HEADERS[i]);
		}
		for (int i = 0; i < projList.size(); i++) {
			proj = projList.get(i);
			row = sheet.createRow(i + 1);
			cell = row.createCell(0);
			cell.setCellValue(proj.getName());
			cell = row.createCell(1);
			cell.setCellValue(proj.getNo());
			cell = row.createCell(2);
			cell.setCellValue(proj.getBu());
			cell = row.createCell(3);
			cell.setCellValue(proj.getDu());
			cell = row.createCell(4);
			cell.setCellValue(proj.getPdu());
			cell = row.createCell(5);
			cell.setCellValue(proj.getArea());
			cell = row.createCell(6);
			cell.setCellValue(proj.getPm());
			cell = row.createCell(7);
			cell.setCellValue(proj.getType());
			cell = row.createCell(8);
			if (null != proj.getStartDate()) {
				cell.setCellStyle(cellStyle);
				cell.setCellValue(proj.getStartDate());
			} else {
				cell.setCellValue("");
			}
			cell = row.createCell(9);
			if (null != proj.getEndDate()) {
				cell.setCellStyle(cellStyle);
				cell.setCellValue(proj.getEndDate());
			} else {
				cell.setCellValue("");
			}
			cell = row.createCell(10);
			if (null != proj.getImportDate()) {
				cell.setCellStyle(cellStyle);
				cell.setCellValue(proj.getImportDate());
			} else {
				cell.setCellValue("");
			}
			cell = row.createCell(11);
			cell.setCellValue(proj.getStatus());

		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			wb.write(os);
		} catch (IOException e) {
			LOG.error("export excel failed!", e);
		}
		return os.toByteArray();
	}

	public ProjectInfo getProjInfo(String buName, String projNo) {
		return projectListDao.getProjInfo(buName, projNo);
	}

	public Map<String, Object> getProjectSelectInfo() {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("businessUnit", projectListDao.getBusinessUnit());
		result.put("deliveryUnit", projectListDao.getDeliveryUnit());
		result.put("countArea", projectListDao.getCountArea());
		result.put("projectType", projectListDao.getProjectType());
		result.put("projectManager", projectListDao.getProjectManager());
		return result;
	}

	public Map<String,String> insertInfo(ProjectDetailInfo proj, String param,
			ProjectManager projManger, Date imptDate) {
		Map<String,String> result=new HashMap<String,String>();
		String message;
		if (param.equals("add")) {
			int projInfoRow = projectListDao.insertInfo(proj);
			int projParamRow = insertProjParam(proj, imptDate);
			int memberRow = projectListDao.insertMember(projManger);
			if (projInfoRow > 0 && projParamRow > 0 && memberRow > 0) {
				message = "保存成功";
			} else {
				message = "保存失败";
			}

		} else {
			int projInfoRow = projectListDao.updateInfo(proj);
			int memberRow = projectListDao.updateMemberInfo(projManger);
			if (projInfoRow > 0 && memberRow > 0) {
				message = "保存成功";
			} else {
				message = "保存失败";
			}
		}
		result.put("param",param);
		result.put("message",message);
		return result;

	}

	public Map<String, Object> queryName(String projNo) {
		ProjectManager proManager = null;
		try {
			proManager = projectListDao.isExitMember(projNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", proManager);
		return result;
	}

	public int insertProjParam(ProjectDetailInfo proj, Date imptDate) {
		List<ParameterInfo> paramInfos = parameterInfo.queryParameterInfo();
		List<Object> listInfo = new ArrayList<Object>();
		for (ParameterInfo paramInfo : paramInfos) {
			Map<String, Object> mapInfo = new HashMap<String, Object>();
			mapInfo.put("no", proj.getNo());
			mapInfo.put("id", paramInfo.getId());
			mapInfo.put("unit", paramInfo.getUnit());
			mapInfo.put("source_value", paramInfo.getSource_value());
			mapInfo.put("parameter", paramInfo.getParameter());
			mapInfo.put("isDisplay", 1);
			mapInfo.put("create_date", imptDate);
			mapInfo.put("creator", "admin");
			mapInfo.put("update_date", imptDate);
			mapInfo.put("update_user", "admin");
			listInfo.add(mapInfo);
		}
		return projectParameter.insertParamInfo(listInfo);
	}

	public Map<String, Object> importProjects(MultipartFile file) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProjectDetailInfo> projInfoLists = new ArrayList<ProjectDetailInfo>();
		List<ProjectManager> projMemberInfo = new ArrayList<ProjectManager>();
		InputStream is;
		Workbook workbook=null;
		  try {
			 is = file.getInputStream();
			 workbook = new XSSFWorkbook(is);
			} catch (IOException e) {
				LOG.error("read file failed!", e);
				result.put("STATUS", "FAILED");
				result.put("MESSAGE", "读取文件失败！");
				return result;
			}
			Sheet sheet = workbook.getSheetAt(0);
			int rowSize = sheet.getLastRowNum();
			  Row row = null;
			  if(rowSize<1){
				  result.put("STATUS", "FAILED");
				  result.put("MESSAGE", "没有要导入的数据！");
				  return result; 
			  }
				for (int i = 1; i <= rowSize; i++) {
					row = sheet.getRow(i);
					ProjectDetailInfo projInfo = new ProjectDetailInfo();
					String cell01Value =getCellFormatValue(row.getCell(0));
					String cell02Value=getCellFormatValue(row.getCell(1));
					if(cell01Value==null && cell02Value==null && rowSize==1){
						result.put("STATUS", "FAILED");
						result.put("MESSAGE", "没有要导入的数据！");
					    return result;
							   }
					String cell03Value = isProjectType(getCellFormatValue(row.getCell(2)));
					String cell04Value=isPayType(getCellFormatValue(row.getCell(3)));
					String cell05Value =getBU(getCellFormatValue(row.getCell(4)));
					String cell06Value = getCellFormatValue(row.getCell(5));
					String cell06Values=getPDU(cell05Value, cell06Value);
					String cell07Value = isPayDepart(getCellFormatValue(row.getCell(6)));
					String cell08Value = isArea(getCellFormatValue(row.getCell(7)));
					String cell09Value = getCellFormatValue(row.getCell(8));
					String cell10Value = getCellFormatValue(row.getCell(9));
					String cell11Value = getCellFormatValue(row.getCell(10));
					String cell12Value = getCellFormatValue(row.getCell(11));
					String cell13Value = getCellFormatValue(row.getCell(12));
					String cell14Value = getCellFormatValue(row.getCell(13));
					Date cell15Value = CovertDate(row.getCell(14));
					Date cell16Value = CovertDate(row.getCell(15));
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					projInfo.setName(cell01Value);
					projInfo.setNo(cell02Value);
					projInfo.setProjectType(cell03Value);
					projInfo.setType(cell04Value);
					projInfo.setBu(cell05Value);
					projInfo.setPdu(cell06Values);
					projInfo.setDu(cell07Value);
					projInfo.setArea(cell08Value);
					projInfo.setPredictMoney(StringToDouble(cell09Value));
					projInfo.setCountOfDay(StringToDouble(cell10Value));
					projInfo.setCountOfMonth(StringToDouble(cell11Value));
					projInfo.setWorkerCount(StringToDouble(cell12Value));
					projInfo.setPo(cell14Value);
					projInfo.setStartDate(cell15Value);
					projInfo.setEndDate(cell16Value);
					String date = df.format(new Date());
					Date imptDate=null;
					try {
						imptDate = df.parse(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					projInfo.setImportDate(imptDate);
					projInfo.setImportUser("admin");
					if (NameAndNo(projInfo.getName(),projInfo.getNo())==null
			            &&("不存在项目").equals(judgeProjInfo(projInfo.getNo()))
					     ){  
						insertProjParam(projInfo,imptDate);
						projInfoLists.add(projInfo);
						ProjectManager projMember = new ProjectManager();
						projMember.setNo(cell02Value);
						projMember.setName(cell13Value);
						projMember.setPosition("PM");
						if(cell13Value==null){
						projMember.setAccount("pm");	
						}else{
						 projMember.setAccount(cell13Value);
						}
						projMember.setStartDate(projInfo.getStartDate());
						projMember.setEndDate(projInfo.getEndDate());
						projMember.setImportDate(imptDate);
						if(("不存在项目信息").equals(judgeMemberInfo(projInfo.getNo()))){
							projMemberInfo.add(projMember);
						}
					}
				}
			int projInfoRow = 0;
			if (projInfoLists != null && projInfoLists.size() != 0) {
				projInfoRow = projectListDao.insertProjInfos(projInfoLists);
			}
			if (projMemberInfo != null && projMemberInfo.size() != 0) {
				int projMemberRow = projectListDao
						.insertProjMembers(projMemberInfo);
			}
			result.put("STATUS", "SUCCESS");
			result.put("size", projInfoRow);
			result.put("MESSAGE", "数据有误，请重新输入！");
		   return result;
	}
	
	public String NameAndNo(String name,String no){
		 String message=null;
		  if(name==null || no==null){
			 message="不能为空";
		 }
		  return message;
	}
	
	
	public String getBU(String bu) {
		List<OrganizeInfo> orgInfos = orgInfoDao.getBU(bu);
		if (orgInfos.size() == 0) {
			   bu=null;
		}
		return bu;
	}
	
	public String getPDU(String bu,String pdu) {
		List<OrganizeInfo> orgInfos = orgInfoDao.getBU(pdu);
	    if (orgInfos.size() == 0) {
		      pdu=null;
			  return pdu;
		 }
		 if(bu==null){
		   return pdu;
		}
	    List<String> list = new ArrayList<String>();	
		List<OrganizeInfo> orgInfo = orgInfoDao.getBU(bu);
		for (OrganizeInfo infos : orgInfos) {
			infos.setLevel(0);
			infos.setNodeName(bu);
			int nodeId = orgInfoDao.getNodeId(infos);
			List<OrganizeInfo> orgs = orgInfoDao.getPDU(nodeId);
			for (OrganizeInfo org : orgs) {
				list.add(org.getNodeName());
			}
		}
		if (!list.contains(pdu)) {
			pdu=null;
		 }
		return pdu;
	}
	
	public String judgeProjInfo(String no) {
		String projMessage=null;
		ProjectDetailInfo proj=projectListDao.isExit(no);
		if(proj!=null){
		  projMessage="已存在项目";
		}else{
		  projMessage="不存在项目";
		}
		return  projMessage;
	}
	
	public String judgeMemberInfo(String no) {
		String MemberMessage=null;
		ProjectManager projM=projectListDao.isExitMember(no);
		if(projM!=null){
			MemberMessage="项目信息存在";
		}else{
			MemberMessage="不存在项目信息";
		}
		return  MemberMessage;
	}
	
	

	private String getCellFormatValue(Cell cell) {
		String strCell =null;
		if (cell == null) {
			return strCell;
		}
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		default:
			strCell =null;
			break;
		}
		return strCell;
	}

	public Date CovertDate(Cell cell) {
		Date dates = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null == cell) {
			return dates;
		}
		try {
			Date cellDate = cell.getDateCellValue();
			if (null == cellDate) {
				return dates;
			}
			String date = df.format(cellDate);
			dates = df.parse(date);
		} catch (ParseException e) {
			dates=null;
		}catch(IllegalStateException e){
			dates=null;
		}

		return dates;
	}

	public Double StringToDouble(String str) {
		try{
		  return Double.parseDouble(str);
		}catch(NullPointerException e){
			return null;
		}catch(Exception e){
			return null;
		}
		
	}
		
	
	public String isProjectType(String projectType){
		CodeMasterInfo codeInfo=new CodeMasterInfo();
		List<String> list=new ArrayList<String>();
		if(projectType==null){
			projectType=null;
		}
		codeInfo.setCodekey("ProjectType");
		List<CodeMasterInfo> codeMaster=codeMasterInfo.getList(codeInfo);
		for(CodeMasterInfo code:codeMaster){
			list.add(code.getName());
		}
		if(!list.contains(projectType)){
			projectType=null;
		}
	  return projectType;
	}
	
   public String isPayType(String payType){
	   CodeMasterInfo codeInfo=new CodeMasterInfo();
		List<String> list=new ArrayList<String>();
		if(payType==null){
			payType=null;
		}
		codeInfo.setCodekey("PayType");
		List<CodeMasterInfo> codeMaster=codeMasterInfo.getList(codeInfo);
		for(CodeMasterInfo code:codeMaster){
			list.add(code.getName());
		}
		if(!list.contains(payType)){
			payType=null;
		}
		return payType;
	}
   
   public String isPayDepart(String payDepart){
	   CodeMasterInfo codeInfo=new CodeMasterInfo();
		List<String> list=new ArrayList<String>();
		if(payDepart==null){
			payDepart=null;
		}
		codeInfo.setCodekey("DeliveryDepartment");
		List<CodeMasterInfo> codeMaster=codeMasterInfo.getList(codeInfo);
		for(CodeMasterInfo code:codeMaster){
			list.add(code.getName());
		}
		if(!list.contains(payDepart)){
			payDepart=null;
		}
		return payDepart;
	}
   
   public String isArea(String area){
	   CodeMasterInfo codeInfo=new CodeMasterInfo();
		List<String> list=new ArrayList<String>();
		if(area==null){
			area=null;
		}
		codeInfo.setCodekey("area");
		List<CodeMasterInfo> codeMaster=codeMasterInfo.getList(codeInfo);
		for(CodeMasterInfo code:codeMaster){
			list.add(code.getName());
		}
		if(!list.contains(area)){
			area=null;
		}
		return area;
	}
}