package com.softtek.mdm.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 排序分页面参数
 * */
public class DataGridModel  implements java.io.Serializable {
	
	private static final long serialVersionUID = 7232798260610351343L;
	
	private int begin = 0;//起始行
	
	private int num = 10; //每页大小,名字必须为rows
	
	/**
	 * 防止表单重复提交token
	 */
	private String token;
	
	/**
	 * android WIFI配置
	 */
	private String list;
	
	/**
	 * ios WIFI配置
	 */
	private String iosList;
	
	/**
	 * WebClip配置
	 */
	private String webClipList;
	
	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getIosList() {
		return iosList;
	}

	public void setIosList(String iosList) {
		this.iosList = iosList;
	}

	public String getWebClipList() {
		return webClipList;
	}

	public void setWebClipList(String webClipList) {
		this.webClipList = webClipList;
	}



	private Map<String,Object> params = new HashMap<String,Object>(0);

	public Map<String, Object> getParams() {
		return params;
	}
	
	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
