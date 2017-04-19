package com.f2b.security.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/2.
 */
@Entity
@Table(name = "base_channel")
@GenericGenerator(name = "BASE_CHANNEL_GEN", strategy = "enhanced-table",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "table_name", value = "table_generator"),
                @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "segment_name"),
                @org.hibernate.annotations.Parameter(name = "segment_value", value = "channel_id"),
                @org.hibernate.annotations.Parameter(name = "value_column_name", value = "next"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "10"),
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo")
        }
)
public class Channel {
    /**
     * 主键
     */
    private Long channelId;

    /**
     * 入口
     */
    private String entrance;

    /**
     * openid
     */
    private String openid;

    /**
     *进入时间
     */
    private Date enterTime;


    @Id
    @GeneratedValue(generator = "BASE_CHANNEL_GEN")
    @Column(name = "channel_id")
    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }
}
