package com.f2b.security.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/2.
 */
@Entity
@Table(name = "access_token")
@GenericGenerator(name = "ACCESS_TOKEN_GEN", strategy = "enhanced-table",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "table_name", value = "table_generator"),
                @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "segment_name"),
                @org.hibernate.annotations.Parameter(name = "segment_value", value = "token_id"),
                @org.hibernate.annotations.Parameter(name = "value_column_name", value = "next"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "10"),
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo")
        }
)
public class AccessToken {
    /**
     * 主键
     */
    private Long tokenId;
    /**
     * token字符串
     */
    private String token;
    /**
     * 有效时间，秒
     */
    private int expiresIn;

    /**
     * 最后一次更新时间
     */
    private Date updateTime;

    @Id
    @GeneratedValue(generator = "ACCESS_TOKEN_GEN")
    @Column(name = "token_id")
	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

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
