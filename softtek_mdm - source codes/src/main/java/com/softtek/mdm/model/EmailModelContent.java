package com.softtek.mdm.model;

/**
 * 邮件模板配置 date: Jun 2, 2016 3:13:53 PM
 *
 * @author brave.chen
 */
public class EmailModelContent
{
	/**
	 * id
	 */
	private Integer id;

	/**
	 * 邮件通知类型（0：违规通知邮件、1：用户批量导入通知邮件、2：密码过期提醒邮件、3：测试邮件）
	 */
	private Integer adviceType;

	/**
	 * 邮件主题
	 */
	private String theme;

	/**
	 * 内容
	 */
	private String content;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getAdviceType()
	{
		return adviceType;
	}

	public void setAdviceType(Integer adviceType)
	{
		this.adviceType = adviceType;
	}

	public String getTheme()
	{
		return theme;
	}

	public void setTheme(String theme)
	{
		this.theme = theme;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("EmailModelContent [id=");
		builder.append(id);
		builder.append(", adviceType=");
		builder.append(adviceType);
		builder.append(", theme=");
		builder.append(theme);
		builder.append(", content=");
		builder.append(content);
		builder.append("]");
		return builder.toString();
	}
}
