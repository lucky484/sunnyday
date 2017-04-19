package com.softtek.mdm.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.softtek.mdm.helper.breadcrumb.BreadCrumbLink;
import com.softtek.mdm.helper.breadcrumb.Link;
/**
 * 面包屑
 * @author color.wu
 *
 */
public class BreadCrumbInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 面包屑的key
	 */
	private static final String BREAD_CRUMB_LINKS = "breadCrumb";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Annotation[] declaredAnnotations = getDeclaredAnnotationsForHandler(handler);
		HttpSession session = request.getSession();
		//先保存原先的面包屑
		Object object=session.getAttribute("currentBreadCrumb");
		if(object instanceof LinkedList){
			@SuppressWarnings("unchecked")
			LinkedList<BreadCrumbLink> list=(LinkedList<BreadCrumbLink>)object;
			if(CollectionUtils.isEmpty(list)){
				session.setAttribute("currentBreadCrumb", new LinkedList<BreadCrumbLink>());
			}
		}
		
		if(declaredAnnotations!=null){
			for (Annotation annotation : declaredAnnotations) {
				if (annotation.annotationType().equals(Link.class)) {
					processAnnotation(request, session, annotation);
				}
			}
		}
		return true;
	}
	
	private void processAnnotation(HttpServletRequest request,HttpSession session,Annotation annotation){
		Link link=(Link) annotation;
		String family = link.family();
		@SuppressWarnings("unchecked")
		Map<String, LinkedHashMap<String, BreadCrumbLink>> breadCrumb=
				(Map<String, LinkedHashMap<String, BreadCrumbLink>>) session.getAttribute(BREAD_CRUMB_LINKS);
		
		if(breadCrumb == null){
			breadCrumb=new HashMap<String, LinkedHashMap<String,BreadCrumbLink>>();
			session.setAttribute(BREAD_CRUMB_LINKS, breadCrumb);
		}
		
		LinkedHashMap<String, BreadCrumbLink> familyMap = breadCrumb.get(family);
		if(familyMap==null){
			familyMap=new LinkedHashMap<String, BreadCrumbLink>();
			breadCrumb.put(family, familyMap);
		}
		
		BreadCrumbLink breadCrumbLink = null;
		breadCrumbLink = currBreadCrumbLink(request, link, familyMap);
		LinkedList<BreadCrumbLink> currentBreadCrumb = new LinkedList<BreadCrumbLink>();
		generateBreadCrumbsRecursively(breadCrumbLink, currentBreadCrumb);
		session.setAttribute("currentBreadCrumb", currentBreadCrumb);
		session.setAttribute(BREAD_CRUMB_LINKS, breadCrumb);
		
	}
	
	private BreadCrumbLink currBreadCrumbLink(HttpServletRequest request,Link link,LinkedHashMap<String,BreadCrumbLink> familyMap){
		BreadCrumbLink breadCrumbLink;
		BreadCrumbLink breadCrumbObject = familyMap.get(link.label());
		
		if(!CollectionUtils.isEmpty(familyMap.values())){
			for (BreadCrumbLink lbreadCrumbLink : familyMap.values()) {
				lbreadCrumbLink.setCurrentPage(false);
			}
		}
		
		if (breadCrumbObject != null) {
			breadCrumbObject.setCurrentPage(true);
			breadCrumbLink = breadCrumbObject;
		} else {
			breadCrumbLink = new BreadCrumbLink(link.family(), link.label(), true, link.parent(),link.belong());
			String fullURL = request.getRequestURL().toString();
			String queryString = request.getQueryString();
			if (queryString != null) {
				fullURL = new StringBuffer(fullURL).append("?").append(queryString).toString();
			}
			breadCrumbLink.setUrl(fullURL);
			createRelationships(familyMap, breadCrumbLink);
			familyMap.put(link.label(), breadCrumbLink);
		}
		return breadCrumbLink;
	}
	
	private Annotation[] getDeclaredAnnotationsForHandler(Object handler) {
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
			return declaredAnnotations;
		}else{
			return null;
		}
	}
	
	private void generateBreadCrumbsRecursively(BreadCrumbLink link, LinkedList<BreadCrumbLink> breadCrumbLinks) {
		if (link.getPrevious() != null) {
			generateBreadCrumbsRecursively(link.getPrevious(), breadCrumbLinks);
		}
		breadCrumbLinks.add(link);
	}
	
	private void createRelationships(LinkedHashMap<String, BreadCrumbLink> familyMap, BreadCrumbLink newLink) {
		if(!CollectionUtils.isEmpty(familyMap.values())){
			for (BreadCrumbLink breadCrumbLink : familyMap.values()) {
				if (breadCrumbLink.getLabel().equalsIgnoreCase(newLink.getParentKey())) {
					breadCrumbLink.addNext(newLink);
					newLink.setPrevious(breadCrumbLink);
					newLink.setParent(breadCrumbLink);
				}
			}
		}
	}
}
