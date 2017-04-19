/*
 * @Title: FSliderWaps.java
 * @Package com.f2b2c.eco.model.platform
 * Copyright: Copyright (c) 2016 
 * Company: Softtek
 * 
 * @author ligang.yang@softtek.com
 * @date 2016年9月14日 上午10:52:08
 * @version V1.0
 */
package com.f2b2c.eco.model.platform;

import java.util.Date;

/**
 * @Description: wap广告图
 * @author ligang.yang@softtek.com
 * @date 2016年9月14日 上午10:52:08
 *
 */
public class FSliderWapsModel
{
    private Integer id;

    private String wapImgUrl;
    
    private String status;

    private String createName;

    private String updateName;

    private Date createTime;

    private Date updateTime;

	private Integer type;

	private String content;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getId()
    {
        return id;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(Integer id)
    {
        this.id = id;
    }

    public String getWapImgUrl()
    {
        return wapImgUrl;
    }

    public void setWapImgUrl(String wapImgUrl)
    {
        this.wapImgUrl = wapImgUrl;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getCreateName()
    {
        return createName;
    }

    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

}
