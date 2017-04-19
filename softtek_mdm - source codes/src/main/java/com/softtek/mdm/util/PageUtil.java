package com.softtek.mdm.util;

import java.util.List;

public class PageUtil {
     
	  //当前页
	  private int page = 1;
	  
	  //页容量
	  private int pageNo = 10;
	  
	  private int pageSize = 10;
	  
	  //总条数
	  private int totalCount;
	  
	  //总页数
	  private int totalPage;
	  
	  private int start;
	  
	  @SuppressWarnings("rawtypes")
	private List list;
	  /**
	   * 通过构造方法传入当前页和总条数
	   * @param totalCount
	   * @param page
	   */
	public PageUtil(int totalCount,int page) {
		this.totalCount = totalCount;
		this.page = page;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize; 
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		totalPage = getTotalCount() / getPageNo();
		return getTotalCount() % getPageNo() == 0 ? totalPage : totalPage + 1;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStart() {
		start = (getPage() - 1) * pageSize;
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}
	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
