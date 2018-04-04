package com.icss.mvp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;



public class MonthUtil {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		Date day=new Date();
		System.out.println(Integer.parseInt(sdf.format(day)));
	}

}
