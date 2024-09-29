package com.hbc.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.hbc.entity.LoginManagement;
import com.hbc.service.LoginManagementService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionFilter implements Filter {
	
	@Autowired
	LoginManagementService loginManagementService;
	
	private static final List<String> URL_PATTERNS = List.of("/api/v1/admin/tournament-location",
			"/api/v1/admin/tournament",
			"/api/v1/logout",
			"/api/v1/user");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String requestURI = req.getRequestURI();

		if (URL_PATTERNS.stream().anyMatch(pattern -> requestURI.startsWith(pattern))) {
			
			String userId = req.getHeader("userId");
			String token = req.getHeader("token");
            LoginManagement loginManagement = loginManagementService.findByUserIdAndToken(Long.parseLong(userId), token);
			
            if (ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(token) || ObjectUtils.isEmpty(loginManagement)) {
            	res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
            }
		}

		chain.doFilter(request, response);
	}
}
