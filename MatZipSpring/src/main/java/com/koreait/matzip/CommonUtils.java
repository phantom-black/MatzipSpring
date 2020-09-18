package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;

public class CommonUtils {
	public static int parseStrToInt(String str) {
		return parseStrToInt(str, 0);
	}
	
	public static int parseStrToInt(String str, int n) {
		try {
			return Integer.parseInt(str);
		} catch(Exception e) {
			return n;
		}
	}
	
	public static double parseStrToDbl(String str) {
		return parseStrToDbl(str, 0);
	}
	
	public static double parseStrToDbl(String str, double n) {
		try {
			return Double.parseDouble(str);
		} catch(Exception e) {
			return n;
		}
	}
	
	public static int getIntParameter(String key, HttpServletRequest request) {
		return parseStrToInt(request.getParameter(key));
	}
	
	public static int getIntParameter(String key, MultipartRequest request) {
		return parseStrToInt(request.getParameter(key));
	}
	
	public static double getDblParameter(String key, HttpServletRequest request) {
		return parseStrToDbl(request.getParameter(key));
	}
}
