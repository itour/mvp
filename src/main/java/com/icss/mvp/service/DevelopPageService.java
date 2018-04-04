package com.icss.mvp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.icss.mvp.dao.ISvnTaskDao;
import com.icss.mvp.entity.GerenCode;
import com.icss.mvp.entity.SvnLogs;

@Service
public class DevelopPageService
{
    @Resource
    private ISvnTaskDao dao;
    
    public List<GerenCode> developSearch(String no){
    	List<GerenCode> list = new ArrayList<GerenCode>();
    	List<String> authors = new ArrayList<String>();
    	List<Integer> months =new ArrayList<Integer>();
    	
    	List<SvnLogs> svnLogs = dao.serchSvn(no);
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("MM");
    	for (SvnLogs svn : svnLogs) {
    		String author = svn.getAUTHOR();
    		int month=Integer.parseInt(sdf.format(svn.getCOMMITTIME()));
    		authors.add(author);
		}
    	Set<String> set=new HashSet<String>(authors);
    	authors=new ArrayList<String>(set);
    	
    	for (int i = 0; i < authors.size(); i++) {
    		GerenCode gerenCode= new GerenCode();
    		String author = authors.get(i);
    		
    		months = dao.searchByAuthor(author);
    		Set<Integer> hs=new HashSet<Integer>(months);
    		months =new ArrayList<Integer>(hs);
    		gerenCode.setName("");
    		gerenCode.setNumber(author);
			gerenCode.setJanuary(0);
	    	gerenCode.setFebruary(0);
	    	gerenCode.setMarch(0);
	    	gerenCode.setApril(0);
	    	gerenCode.setMay(0);
	        gerenCode.setJune(0);
	    	gerenCode.setJuly(0);
	    	gerenCode.setAugust(0);
	    	gerenCode.setSeptember(0);
	    	gerenCode.setOctober(0);
	    	gerenCode.setNovember(0);
	    	gerenCode.setDecember(0);
    		for (int j = 1; j < 13; j++) {
    			int sum = 0;
    			if(months.contains(j)) {
    				sum = dao.addCode(j, no, author); 
    			}
    			if(j == 1) {
            		gerenCode.setJanuary(sum);
            	}else if(j == 2) {
            		gerenCode.setFebruary(sum);
            	}else if(j == 3) {
            		gerenCode.setMarch(sum);
            	}else if(j == 4) {
            		gerenCode.setApril(sum);
            	}else if(j == 5) {
            		gerenCode.setMay(sum);
            	}else if(j == 6) {
            		gerenCode.setJune(sum);
            	}else if(j == 7) {
            		gerenCode.setJuly(sum);
            	}else if(j == 8) {
            		gerenCode.setAugust(sum);
            	}else if(j == 9) {
            		gerenCode.setSeptember(sum);
            	}else if(j == 10) {
            		gerenCode.setOctober(sum);
            	}else if(j == 11) {
            		gerenCode.setNovember(sum);
            	}else if(j == 12) {
            		gerenCode.setDecember(sum);
            	}
			}
    		list.add(gerenCode);
    	}
		return list;
	}
}
