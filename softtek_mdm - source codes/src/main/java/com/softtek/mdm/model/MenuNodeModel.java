package com.softtek.mdm.model;

import java.util.List;

/**
 * 用于递归处理菜单
 * @author color.wu
 *
 */
public class MenuNodeModel {
	/**
	 * 当前节点
	 */
	private MenuModel current;
	/**
	 * 节点的孩子
	 */
	private List<MenuNodeModel> children;

	public MenuModel getCurrent() {
		return current;
	}

	public void setCurrent(MenuModel current) {
		this.current = current;
	}

	public List<MenuNodeModel> getChildren() {
		return children;
	}

	public void setChildren(List<MenuNodeModel> children) {
		this.children = children;
	}
}
