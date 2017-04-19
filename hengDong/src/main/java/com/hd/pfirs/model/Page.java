package com.hd.pfirs.model;

import java.util.List;

public class Page {
     
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
	  
	  private List list;
	  /**
	   * 通过构造方法传入总记录数和当前页
	   * @param totalCount
	   * @param page
	   */
	public Page(int totalCount,int page) {
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
		return page * pageSize;
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
	public List getList() {
		return list;
	}
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
