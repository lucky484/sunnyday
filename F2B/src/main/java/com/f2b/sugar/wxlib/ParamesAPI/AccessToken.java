package com.f2b.sugar.wxlib.ParamesAPI;

import java.util.Date;

/**
 * 微信通用接口凭证
 *
 * @author ivhhs
 * @date 2014.10.16
 */
public class AccessToken {
	// 获取到的凭证
	private String token;

	// 凭证有效时间，单位：秒
	private int expiresIn;

	private Date updateTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}