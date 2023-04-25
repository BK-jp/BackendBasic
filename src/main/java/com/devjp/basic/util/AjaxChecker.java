package com.devjp.basic.util;

import javax.servlet.http.HttpServletRequest;

public class AjaxChecker {
	public static boolean check(HttpServletRequest request) {
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			return true;
		}else {
			return false;
		}
	}
}
