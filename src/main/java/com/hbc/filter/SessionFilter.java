package com.hbc.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hbc.constant.SessionConst;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionFilter implements Filter {
	
	private static final List<String> URL_PATTERNS = List.of("/api/v1/tournament-location/*",
			"/api/v1/logout",
			"/api/v1/tournament/*");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String requestURI = req.getRequestURI();

		if (URL_PATTERNS.stream().anyMatch(pattern -> requestURI.startsWith(pattern))) {
			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute(SessionConst.CURRENT_USER) == null) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
			}
		}

		chain.doFilter(request, response);
	}
}
