package com.f2b.security.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/24.
 */
@Entity
@Table(name = "base_record")
@GenericGenerator(name = "BASE_RECORD_GEN", strategy = "enhanced-table",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "table_name", value = "table_generator"),
                @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "segment_name"),
                @org.hibernate.annotations.Parameter(name = "segment_value", value = "record_id"),
                @org.hibernate.annotations.Parameter(name = "value_column_name", value = "next"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "10"),
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo")
        }
)
public class Record {


    /**
     * 主键
     */
    private Long recordId;

    /**
     *  中奖人的openId
     */
    private String openId;

    /**
     *  中奖人的昵称nickname
     */
    private String nickname;

    /**
     * 抽中奖品
     */
    private String award;

    /**
     * 中奖时间
     */
    private Date lotteryDate;

    /**
     * 是否已经领取(0：未领取，1：已经领取)
     */
    private Integer drawStatus=0;

    /**
     * 中奖的商品订单号
     */
    private String orderNo;
    
    /**
     * 抽奖人的ip
     */
    private String ip;

    @Id
    @GeneratedValue(generator = "BASE_RECORD_GEN")
    @Column(name = "record_id")
    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public Date getLotteryDate() {
        return lotteryDate;
    }

    public void setLotteryDate(Date lotteryDate) {
        this.lotteryDate = lotteryDate;
    }

        public Integer getDrawStatus() {
        return drawStatus;
    }

    public void setDrawStatus(Integer drawStatus) {
        this.drawStatus = drawStatus;
    }

    public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
    
}
