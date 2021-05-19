package com.javaee.ch17.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class MyUtils {
	public static final String shortDateFormat="yyyy-MM-dd";
	public static final String longDateTimeFormat="yyyy-MM-dd HH:mm:ss"; 
	public static final String longDateTimeWithMillisFormat="yyyy-MM-dd HH:mm:ss.SSS";// yyyy-MM-dd HH:mm:ss.SSS 精确到毫秒
	private static String productImageURLRoot="/upload/productImages/", tempDirectory="/upload/temp/";
	public static String getDateFormat(int theType) {
		String temp=longDateTimeWithMillisFormat;
		switch (theType) {
		case 0:
			temp=shortDateFormat;
			break;		
		case 1:
			temp=longDateTimeFormat;
			break;	
		default:
			temp=longDateTimeWithMillisFormat;
		}	
		return temp;
	}
	
	public static String getProductImageURLRoot() {
		return productImageURLRoot;
	}

	public static String getTempDirectory() {
		return tempDirectory;
	}

	public static String getDateTimeString(Date theDate, int theType) {
		if(theDate==null) {
			return "无";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat(theType));
		return sdf.format(theDate);
	}	
	public static String getDateTimeString(int theType) {		
		Date theDate = new Date();
		return getDateTimeString(theDate, theType);
	}	
	public static String getDateTimeString(Date theDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat(2));
		return sdf.format(theDate);
	}
	public static String getDateTimeString() {		
		Date theDate = new Date();
		return getDateTimeString(theDate);
	}	
	public static String getDateTimePureString() {		
		Date theDate = new Date();
		//String temp=getDateTimeString(theDate);
		//return temp.replace("-", "").replace(" ", "").replace(":", "").replace(".", "");
		return getDateTimePureString(new Date());
	}	
	public static String getDateTimePureString(Date theDate) {		
		return getDateTimeString(theDate).replace("-", "").replace(" ", "").replace(":", "").replace(".", "");
	}

	public static String getImageSrc(String theType,String theImagePathLeft) {
		String thePrefix = "";
		if("1".equals(theType)) {
			thePrefix = productImageURLRoot;
		}
		return thePrefix+theImagePathLeft;
	}
	public static String trim(String theStr) {
		if(theStr==null) {
			return null;
		}
		return theStr.trim();
	}
	public static void main(String[] args) {
		System.out.println(getDateTimeString());
		System.out.println(getDateTimeString(1));
		long currentTimeMillseconds = System.currentTimeMillis();// 当前时间距离1970年1月1日0点0分0秒所经历的毫秒数
		long nanosecondsStart = System.nanoTime();// 纳秒 二个这样的长整数之差 视为执行时间(纳秒)。 起点
		Date dt= new Date();
		long time= dt.getTime();//距离1970年1月1日0点0分0秒的毫秒数
		System.out.println(System.currentTimeMillis()+" "+ time);//二者相同
		long nanosecondsEnd = System.nanoTime();  // 终点
		System.out.println("执行时间是：" + (nanosecondsEnd-nanosecondsStart) + " ns");// 秒 毫秒 微妙 纳秒  都是1000倍的关系。
		//System.out.println("IP地址："+ getIpAddress(request)); 后台没办法实现。
		System.out.println(getDateTimePureString());// 长度是17
		System.out.println("2020/07/20200712235549768.jpeg".length()); // .png 29  .jpeg 30
	}
	public static void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}