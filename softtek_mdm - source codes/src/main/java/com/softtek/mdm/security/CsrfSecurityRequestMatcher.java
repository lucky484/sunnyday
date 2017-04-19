package com.softtek.mdm.security;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CsrfSecurityRequestMatcher implements RequestMatcher{
	
	private List<String> execludeUrls;
	
	private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
			 
	@Override
	public boolean matches(HttpServletRequest request) {
		 if (CollectionUtils.isNotEmpty(execludeUrls)) {
              String servletPath = request.getServletPath();
              for (String url : execludeUrls) {
                  if (servletPath.contains(url)) {
                      return false;
                  }
              }
          }
       return !allowedMethods.matcher(request.getMethod()).matches();
	}
   
	 	 /**
	      * 需要排除的url列表
	      */
	 
	     public List<String> getExecludeUrls() {
	         return execludeUrls;
	     }
	 
	     public void setExecludeUrls(List<String> execludeUrls) {
	         this.execludeUrls = execludeUrls;
	     }
}
