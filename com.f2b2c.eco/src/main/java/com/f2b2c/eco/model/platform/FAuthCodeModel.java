package com.f2b2c.eco.model.platform;

import java.io.Serializable;
import java.util.Date;

public class FAuthCodeModel implements Serializable {
    /**
     * 授权码唯一主键
     */
    private Integer id;

    /**
     * 创建人
     */
    private FUserModel createdUser;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 是否被使用0没有1有
     */
    private Integer isUsed;

    /**
     * 授权码(20个字符以内)
     */
    private String code;

    private static final long serialVersionUID = 1L;

    public Integer getId()
    {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public FUserModel getCreatedUser()
    {
        return createdUser;
    }

    public void setCreatedUser(FUserModel createdUser)
    {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime()
    {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FAuthCodeModel [id=");
		builder.append(id);
		builder.append(", createdUser=");
		builder.append(createdUser);
		builder.append(", createdTime=");
		builder.append(createdTime);
		builder.append(", isUsed=");
		builder.append(isUsed);
		builder.append(", code=");
		builder.append(code);
		builder.append("]");
		return builder.toString();
	}

   
}