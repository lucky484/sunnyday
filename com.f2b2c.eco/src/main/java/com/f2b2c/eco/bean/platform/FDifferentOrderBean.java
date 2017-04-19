package com.f2b2c.eco.bean.platform;

import java.util.List;

/**
 * 创建补差价订单Bean
 * @author jane.hui
 *
 */
public class FDifferentOrderBean {

    /**
     * 差价订单id
     */
    private String id;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 差价类型
     */
    private Integer diffenceType;
    
    /**
     * 差价原因
     */
    private Integer diffenceCause;
    
    /**
     * 差价金额
     */
    private Integer diffenceAmount;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 订单id
     */
    private String orderId;
    
    /**
     * 订单价格
     */
    private Integer orderPrice;
    
    /**
     * 实际金额
     */
    private Integer realPay;
    
    /**
     * 图片list
     */
    private List<String> imgList;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getDiffenceType() {
        return diffenceType;
    }

    public void setDiffenceType(Integer diffenceType) {
        this.diffenceType = diffenceType;
    }

    public Integer getDiffenceCause() {
        return diffenceCause;
    }

    public void setDiffenceCause(Integer diffenceCause) {
        this.diffenceCause = diffenceCause;
    }

    public Integer getDiffenceAmount() {
        return diffenceAmount;
    }

    public void setDiffenceAmount(Integer diffenceAmount) {
        this.diffenceAmount = diffenceAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
    
    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

	public Integer getRealPay() {
		return realPay;
	}

	public void setRealPay(Integer realPay) {
		this.realPay = realPay;
	}
}