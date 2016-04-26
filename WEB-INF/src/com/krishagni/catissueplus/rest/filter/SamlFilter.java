package com.krishagni.catissueplus.rest.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.krishagni.catissueplus.core.common.util.ConfigUtil;

import edu.emory.mathcs.backport.java.util.Collections;

public class SamlFilter extends FilterChainProxy {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		boolean isSamlEnable = ConfigUtil.getInstance().getBoolSetting("common", "is_saml_enable", false);
		if (isSamlEnable) {
			super.doFilter(request, response, chain); 
		} else {
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest) request).getContextPath());
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void setFilterChain(Map<String, Filter> filters) {
		Map<RequestMatcher, List<Filter>> filterChainMap = new HashMap<RequestMatcher, List<Filter>>();
		for (Map.Entry<String, Filter>entry : filters.entrySet()) {
			filterChainMap.put(new AntPathRequestMatcher(entry.getKey()), Collections.singletonList(entry.getValue()));
		}
		
		setFilterChainMap(filterChainMap);
	}
}
