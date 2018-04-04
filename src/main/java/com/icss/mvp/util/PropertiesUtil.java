package com.icss.mvp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil
{
	private final static InputStreamReader inStream = new InputStreamReader(PropertiesUtil.class
			.getClassLoader().getResourceAsStream("task.properties"));
	private final static Logger LOG = Logger.getLogger(PropertiesUtil.class);

	public static String getValue(String name)
	{
		Properties prop = new Properties();  
		try
		{
			prop.load(inStream);
		} catch (IOException e)
		{
			LOG.error("get properties failed!", e);
		}  
		return prop.getProperty(name);  
	}
}
