package com.hd.pfirs.model;

import java.util.List;

public class MenuModel
{
	/**
	 * 菜单对象
	 */
	private MenuDictModel menuDictModel;

	/**
	 * 子菜单列表
	 */
	private List<MenuModel> subMenuModels;

	public MenuDictModel getMenuDictModel()
	{
		return menuDictModel;
	}

	public void setMenuDictModel(MenuDictModel menuDictModel)
	{
		this.menuDictModel = menuDictModel;
	}

	public List<MenuModel> getSubMenuModels()
	{
		return subMenuModels;
	}

	public void setSubMenuModels(List<MenuModel> subMenuModels)
	{
		this.subMenuModels = subMenuModels;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("MenuModel [MenuDictModel=");
		builder.append(menuDictModel);
		builder.append(", subMenuModels=");
		builder.append(subMenuModels);
		builder.append("]");
		return builder.toString();
	}
}
