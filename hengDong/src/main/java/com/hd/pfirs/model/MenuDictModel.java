package com.hd.pfirs.model;

/**
 * 菜单模型
 * 
 * @author brave.chen
 * @since 2016-01-27
 *
 */
public class MenuDictModel
{
	/**
	 * 菜单ID
	 */
	private Long menuID;

	/**
	 * 上级菜单ID
	 */
	private Long fatherMenuID;

	/**
	 * 菜单名
	 */
	private String menuName;

	/**
	 * 对应值
	 */
	private String menuValue;

	/**
	 * 类型（0-根菜单；1-2级子菜单；2-底层菜单）
	 */
	private String menuType;

	/**
	 * 访问地址
	 */
	private String accessURL;

	/**
	 * 备注
	 */
	private String remarkDesc;

	/**
	 * 删除状态(0-未删除；1-已删除)
	 */
	private String deleteStatus;

	/**
	 * 是否赋权
	 */
	private Boolean isAuth;

	public Long getMenuID()
	{
		return menuID;
	}

	public void setMenuID(Long menuID)
	{
		this.menuID = menuID;
	}

	public Long getFatherMenuID()
	{
		return fatherMenuID;
	}

	public void setFatherMenuID(Long fatherMenuID)
	{
		this.fatherMenuID = fatherMenuID;
	}

	public String getMenuName()
	{
		return menuName;
	}

	public void setMenuName(String menuName)
	{
		this.menuName = menuName;
	}

	public String getMenuValue()
	{
		return menuValue;
	}

	public void setMenuValue(String menuValue)
	{
		this.menuValue = menuValue;
	}

	public String getMenuType()
	{
		return menuType;
	}

	public void setMenuType(String menuType)
	{
		this.menuType = menuType;
	}

	public String getAccessURL()
	{
		return accessURL;
	}

	public void setAccessURL(String accessURL)
	{
		this.accessURL = accessURL;
	}

	public String getRemarkDesc()
	{
		return remarkDesc;
	}

	public void setRemarkDesc(String remarkDesc)
	{
		this.remarkDesc = remarkDesc;
	}

	public String getDeleteStatus()
	{
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus)
	{
		this.deleteStatus = deleteStatus;
	}

	public Boolean getIsAuth()
	{
		return isAuth;
	}

	public void setIsAuth(Boolean isAuth)
	{
		this.isAuth = isAuth;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("MenuDictModel [menuID=");
		builder.append(menuID);
		builder.append(", fatherMenuID=");
		builder.append(fatherMenuID);
		builder.append(", menuName=");
		builder.append(menuName);
		builder.append(", menuValue=");
		builder.append(menuValue);
		builder.append(", menuType=");
		builder.append(menuType);
		builder.append(", accessURL=");
		builder.append(accessURL);
		builder.append(", remarkDesc=");
		builder.append(remarkDesc);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append(", isAuth=");
		builder.append(isAuth);
		builder.append("]");
		return builder.toString();
	}

}
