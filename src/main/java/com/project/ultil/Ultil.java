package com.project.ultil;

import javax.servlet.http.HttpServletRequest;

public class Ultil {
	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
