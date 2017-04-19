package com.f2b2c.eco.model.platform;

/**
 * B端App通知设置
 * @author jzhu
 *
 */
public class BConfigModel {

	private Integer id;
	private String isEnabled;//是否开启服务通知
	private String isDisturbed;//是否夜间免打扰
	private Integer userId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getIsDisturbed() {
		return isDisturbed;
	}
	public void setIsDisturbed(String isDisturbed) {
		this.isDisturbed = isDisturbed;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BConfigModel [id=");
		builder.append(id);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append(", isDisturbed=");
		builder.append(isDisturbed);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}
}

