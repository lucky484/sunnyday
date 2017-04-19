package com.softtek.mdm.model;

/**
 * Description: 黑白名单url
 * date: Apr 12, 2016 4:04:29 PM
 *
 * @author brave.chen
 */
public class BlackWhiteListUrl
{
	/**
	 * 黑名单
	 */
	private Integer id;
	
    /**
     * 黑白名单监测id
     */
    private Integer bwUrlId;
    
    /**
     * 黑白名单那id
     */
    private Integer blackWhiteListId;
    
    /**
     * 黑白名单监听url
     */
    private String url;
    
    /**
     * url名称
     */
    private String urlName;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
     * 
     * Creates a new instance of BlackWhiteListUrl.
     *
     */
    public BlackWhiteListUrl()
    {
        
    }
    
    /**
     * Creates a new instance of BlackWhiteListUrl.
     *
     * @param blackWhiteListId 黑名单ID 
     * @param name 监听url名称
     * @param url 监听url地址
     */
    public BlackWhiteListUrl(Integer blackWhiteListId, String name, String url)
    {
        this.blackWhiteListId = blackWhiteListId;
        this.urlName = name;
        this.url = url;
    }

    public Integer getBwUrlId()
    {
        return bwUrlId;
    }

    public void setBwUrlId(Integer bwUrlId)
    {
        this.bwUrlId = bwUrlId;
    }

    public Integer getBlackWhiteListId()
    {
        return blackWhiteListId;
    }

    public void setBlackWhiteListId(Integer blackWhiteListId)
    {
        this.blackWhiteListId = blackWhiteListId;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrlName()
    {
        return urlName;
    }

    public void setUrlName(String urlName)
    {
        this.urlName = urlName;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BlackWhiteListUrl [bwUrlId=");
        builder.append(bwUrlId);
        builder.append(", blackWhiteListId=");
        builder.append(blackWhiteListId);
        builder.append(", url=");
        builder.append(url);
        builder.append(", urlName=");
        builder.append(urlName);
        builder.append("]");
        return builder.toString();
    }
}

