package com.softtek.mdm.helper.breadcrumb;

import java.util.LinkedList;
import java.util.List;

public class BreadCrumbLink {

	private BreadCrumbLink previous;
	
	private List<BreadCrumbLink> next=new LinkedList<BreadCrumbLink>();
	
	private String url;
	
	private String family;
	
	private String label;
	
	private boolean currentPage;
	
	private String parentKey;
	
	private BreadCrumbLink parent;
	
	private String belong;
	
	/**
	 * 
	 * @param family -
	 * @param label -
	 * @param currentPage -
	 * @param parentKey -
	 */
	public BreadCrumbLink(String family, String label, boolean currentPage, String parentKey,String belong) {
		this.family = family;
		this.label = label;
		this.currentPage = currentPage;
		this.parentKey = parentKey;
		this.belong=belong;
	}
	
	public void addNext(BreadCrumbLink next) {
		this.next.add(next);
	}

	public BreadCrumbLink getPrevious() {
		return previous;
	}

	public void setPrevious(BreadCrumbLink previous) {
		this.previous = previous;
	}

	public List<BreadCrumbLink> getNext() {
		return next;
	}

	public void setNext(List<BreadCrumbLink> next) {
		this.next = next;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(boolean currentPage) {
		this.currentPage = currentPage;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public BreadCrumbLink getParent() {
		return parent;
	}

	public void setParent(BreadCrumbLink parent) {
		this.parent = parent;
	}
	
	public String getBelong(){
		return this.belong;
	}
	
	public void setBelong(String belong){
		this.belong=belong;
	}
	
	
}
