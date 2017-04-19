package com.f2b2c.eco.bean.platform;

import java.util.List;

import com.f2b2c.eco.apimodel.Area;
import com.f2b2c.eco.apimodel.City;
import com.f2b2c.eco.apimodel.Province;

/**
 * 店铺信息
 * 
 * @author jane.hui
 *
 */
public class BShopInfoBean {

    /**
	 * 店铺id
	 */
    private Integer id;
    
    /**
	 * 店铺名称
	 */
    private String shopName;
    
    /**
	 * 授权码
	 */
    private String authCode;
    
    /**
	 * 经度
	 */
    private String locationX;
    
    /**
	 * 维度
	 */
    private String locationY;
    
    /**
	 * 店铺描述
	 */
    private String remark;
    
    /**
	 * 店铺地址
	 */
    private String address;
    
    /**
	 * 区域代码
	 */
    private String areaCode;
    
    /**
	 * 城市代码
	 */
    private String cityCode;
    
    /**
	 * 省代码
	 */
    private String provinceCode;
    
    /**
	 * 专属顾问
	 */
    private String fUserName;

    /**
	 * 店老板
	 */
    private String bUserName;
    
    /**
	 * 手机号
	 */
    private String phone;
    
    /**
	 * 省list
	 */
    private List<Province> provinceList;
    
    /**
	 * 城市list
	 */
    private List<City> cityList;
    
    /**
	 * 区域list
	 */
    private List<Area> areaList;
    
    /**
	 * 省名称
	 */
    private String provinceName;
    
    /**
	 * 市名称
	 */
    private String cityName;
    
    /**
	 * 区域名称
	 */
    private String areaName;
    
	/**
	 * 商品状态 ：1正常 0 失效
	 */
	private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }
    
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public void setShopName(String shopName) {
        this.shopName = shopName;
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

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getfUserName() {
		return fUserName;
	}

	public void setfUserName(String fUserName) {
		this.fUserName = fUserName;
	}

	public String getbUserName() {
		return bUserName;
	}

	public void setbUserName(String bUserName) {
		this.bUserName = bUserName;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Province> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<Province> provinceList) {
        this.provinceList = provinceList;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}