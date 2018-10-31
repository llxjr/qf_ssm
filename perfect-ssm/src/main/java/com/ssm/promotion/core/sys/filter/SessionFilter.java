package com.ssm.promotion.core.sys.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.ssm.promotion.core.common.Constants;

public class SessionFilter extends OncePerRequestFilter {

	private static String[] filterURIs = { "login", "logout", ".", "doLogin" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestURI = request.getServletPath();
		boolean isFilter = Boolean.TRUE;
		for (String filterURI : filterURIs) {
			if (requestURI.contains(filterURI)) {
				isFilter = Boolean.FALSE;
				break;
			}
		}

		if (isFilter) {
			Object object = request.getSession().getAttribute(Constants.SESSION_USER);
			if (object == null) {
				response.sendRedirect(request.getContextPath() + "/login");
			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

}
