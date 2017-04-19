package com.softtek.mdm.resolver;

import java.lang.annotation.Annotation;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.softtek.mdm.annotation.LocaleIn;

public class LocaleResolver implements HandlerMethodArgumentResolver  {


	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if(parameter.hasParameterAnnotation(LocaleIn.class)){
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Annotation[] anns=parameter.getParameterAnnotations();
		if(anns!=null&&anns.length>0){
			for(int i=0;i<anns.length;i++){
				if(LocaleIn.class.isInstance(anns[i])){
					LocaleIn l=(LocaleIn) anns[i];
					HttpServletRequest httprequest = (HttpServletRequest) webRequest.getNativeRequest();  
					String lang=StringUtils.trimToEmpty(httprequest.getHeader(l.value()));
					if(StringUtils.isNotEmpty(lang)){
						return new Locale(lang);
					}else{
						return new Locale(l.language(),l.country());
					}
				}
			}
		}
		return WebArgumentResolver.UNRESOLVED;
	}

}
