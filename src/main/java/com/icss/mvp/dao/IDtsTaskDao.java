package com.icss.mvp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IDtsTaskDao
{
	public void insert(@Param("list") List<Map<String, String>> dtsList);

	public List<HashMap<String, Object>> query();

	public List<HashMap<String, Object>> queryDI();

	public List<HashMap<String, Object>> queryTr5DI();

	public List<HashMap<String, Object>> queryTr6DI();

        public List<HashMap<String,Object>> queryDensity();
 
}
