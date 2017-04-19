package com.f2b.security.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/24.
 */
@Entity
@Table(name = "base_award")
@GenericGenerator(name = "BASE_AWARD_GEN", strategy = "enhanced-table",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "table_name", value = "table_generator"),
                @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "segment_name"),
                @org.hibernate.annotations.Parameter(name = "segment_value", value = "award_id"),
                @org.hibernate.annotations.Parameter(name = "value_column_name", value = "next"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "10"),
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo")
        }
)
public class Award {
    /**
     * 主键
     */
    private Long awardId;

    /**
     * -1 今天抽过不能再抽
     * 0 未中奖
     * 1 Iphone
     * 2 2元现金
     * 3 10元代金券
     * 4 5元现金
     * 5 未中奖
     * 6 1元现金
     * 7 iwatch
     * 8 5元代金券
     */
    private String awards; //三位数字

    /**
     * 奖品的数量
     */
    private Integer number;

    /**
     * 创建时间（注册时间）
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;


    @Id
    @GeneratedValue(generator = "BASE_AWARD_GEN")
    @Column(name = "award_id")
    public Long getAwardId() {
        return awardId;
    }

    public void setAwardId(Long awardId) {
        this.awardId = awardId;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
