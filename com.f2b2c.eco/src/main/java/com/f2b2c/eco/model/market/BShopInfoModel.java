package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

import com.f2b2c.eco.model.common.AreaModel;
import com.f2b2c.eco.model.platform.FUserModel;

public class BShopInfoModel implements Serializable {
    /**
     * 商铺ID
     */
    private Integer id;

    /**
     * 商铺老板ID，商铺用户ID
     */
    private BUserModel user;

    /**
     * 商铺名称
     */
    private String shopName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 身份证号
     */
    private String identityId;

    /**
     * 坐标X
     */
    private String locationX;

    /**
     * 坐标Y
     */
    private String locationY;

    private AreaModel area;
    /**
     * 地址
     */
    private String address;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 创建人
     */
    private FUserModel createdUser;

    /**
     * 更新人
     */
    private FUserModel updatedUser;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 授权码
     */
    private String authCode;

    /**
     * 删除时间
     */
    private Date deletedTime;

    /**
     * 详情
     */
    private String details;
    
    /**
     * 商户外键
     */
    private String bUserId;
    
    private Integer areaId;//店铺的区域id,为了插入用

    /**
     * 是否启用(1.启用 0.禁用)
     */
    private String isEnable;
    
    /**
     * 省代码
     */
    private String provinceCode;
    
    /**
     * 省名称
     */
    private String provinceName;
    
    /**
     * 市名称
     */
    private String cityName;
    
    /**
     * 市代码
     */
    private String cityCode;
    
    /**
     * 地区名称
     */
    private String areaName;
    
    private static final long serialVersionUID = 1L;

    public AreaModel getArea() {
        return area;
    }

    public void setAreaModel(AreaModel area) {
        this.area = area;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BUserModel getUser() {
        return user;
    }

    public void setUser(BUserModel user) {
        this.user = user;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public FUserModel getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(FUserModel createdUser) {
        this.createdUser = createdUser;
    }

    public void setUpdatedUser(FUserModel updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Date getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(Date deletedTime) {
        this.deletedTime = deletedTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public FUserModel getUpdatedUser() {
        return updatedUser;
    }
    
    public String getbUserId() {
        return bUserId;
    }

    public void setbUserId(String bUserId) {
        this.bUserId = bUserId;
    }
    
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public void setArea(AreaModel area) {
        this.area = area;
    }
    
    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }
    
    public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BShopInfoModel [id=");
        builder.append(id);
        builder.append(", user=");
        builder.append(user);
        builder.append(", shopName=");
        builder.append(shopName);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", identityId=");
        builder.append(identityId);
        builder.append(", locationX=");
        builder.append(locationX);
        builder.append(", locationY=");
        builder.append(locationY);
        builder.append(", address=");
        builder.append(address);
        builder.append(", createdTime=");
        builder.append(createdTime);
        builder.append(", createdUser=");
        builder.append(createdUser);
        builder.append(", updatedTime=");
        builder.append(updatedTime);
        builder.append(", authCode=");
        builder.append(authCode);
        builder.append(", deletedTime=");
        builder.append(deletedTime);
        builder.append(", details=");
        builder.append(details);
        builder.append("]");
        return builder.toString();
    }

}