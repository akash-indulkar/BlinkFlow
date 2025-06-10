package com.blinkflow.flowrun_listener.config;

import java.io.IOException;
import jakarta.servlet.Filter;
import org.springframework.stereotype.Component;
import com.blinkflow.flowrun_listener.util.CachedBodyHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CachedBodyFilter implements Filter{
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if ("POST".equalsIgnoreCase(httpRequest.getMethod()) || "PUT".equalsIgnoreCase(httpRequest.getMethod())) {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpRequest);
            chain.doFilter(cachedBodyHttpServletRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
