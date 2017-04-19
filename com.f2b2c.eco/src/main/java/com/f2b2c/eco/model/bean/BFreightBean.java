package com.f2b2c.eco.model.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 运费Bean
 * @author jane.hui
 *
 */
public class BFreightBean {
    
    private String provinceName;
    
    private Integer provinceId;
    
    private Integer id;
    
    private BigDecimal freight;
    
    private Date createTime;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}