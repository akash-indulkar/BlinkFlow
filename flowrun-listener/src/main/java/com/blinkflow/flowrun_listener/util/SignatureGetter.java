package com.blinkflow.flowrun_listener.util;

import java.util.Enumeration;
import jakarta.servlet.http.HttpServletRequest;

public class SignatureGetter {
	public static String getSignatureFromHeader(Enumeration<String> headers, HttpServletRequest request) {
		while(headers.hasMoreElements()) {
			String header = headers.nextElement();
			if(header.toLowerCase().contains("signature")) {
				if(request.getHeader(header).startsWith("sha256")) return request.getHeader(header);
			}
		}
		return null;
	}
}
