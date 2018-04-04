package com.icss.mvp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	
	private static final String DTS_URL = "10.100.14.217";
	private static final String DOMAIN = "CHINA";
	private static final int PORT = 80;
	private static final String WORK_STATION = "myworkstation";
	private static final String SCHEMA_NAME = "ntlm";
	private static final String HTTP = "http";
	private static final String GET_FILE_URL = "/iisstart.png"; 

	private static HttpClientUtil instance = null;

	private HttpClientUtil() {
	}

	public static HttpClientUtil getInstance() {
		if (instance == null) {
			instance = new HttpClientUtil();
		}
		return instance;
	}

	public String createDTSFile(String userName,String password,String projectNo){
		String fileName = "";
		
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		NTCredentials creds = new NTCredentials(userName, password, WORK_STATION, DOMAIN);
		credsProvider.setCredentials(new AuthScope(DTS_URL, PORT, null, SCHEMA_NAME), creds);
		CloseableHttpClient httpclient = HttpClients.custom()
		.setDefaultCredentialsProvider(credsProvider)
		.build();
		try {
			HttpHost target = new HttpHost(DTS_URL, PORT, HTTP);
			// 保证相同的容内来用于执行逻辑相关的请求
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpGet = new HttpGet(GET_FILE_URL);
			CloseableHttpResponse response = httpclient.execute(target, httpGet, localContext);
			try {
				if (response.getStatusLine().getStatusCode() == 200){
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						InputStream is = entity.getContent();
						Date currentTime = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
						String dateString = formatter.format(currentTime);
						String tmpName = projectNo+"_"+dateString+".xls";
						String filePath = PropertiesUtil.getValue("dts_file_path");
						fileName = createFile(filePath,tmpName);
						if (!fileName.equals(null) && !fileName.isEmpty()){
							File file = new File(fileName);
							FileOutputStream fileout = new FileOutputStream(file);
							byte[] buffer = new byte[1024];
							int ch = 0;
							while ((ch = is.read(buffer)) != -1) {
								fileout.write(buffer, 0, ch);
							}
							is.close();
							fileout.flush();
							fileout.close();
						}
					}
				}
				EntityUtils.consume(response.getEntity()); 
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}
	public static String createFile(String dirPath,String fileName){
		try {
			File dir = new File(dirPath);
			if (!dirPath.endsWith(File.separator)) {// 结尾是否以"/"结束
				dirPath = dirPath + File.separator;
			}
			if (!dir.exists()){
				dir.mkdirs();
			}
			String filePathName = dirPath + fileName;
			File file = new File(filePathName);
			if (!file.exists()){
				file.createNewFile();
			}
			return filePathName;
		} catch (Exception e) {// 捕获异常
			e.printStackTrace();
			return "";
		}
	}
}
