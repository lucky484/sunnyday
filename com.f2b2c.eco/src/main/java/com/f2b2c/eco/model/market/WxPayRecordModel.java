package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信支付记录表
 * @author jane.hui
 *
 */
public class WxPayRecordModel implements Serializable {
    /**
     * 主键
     * @mbggenerated
     */
    private Integer id;

    /**
     * 应用id
     * @mbggenerated
     */
    private String appid;

    /**
     * 商户号
     * @mbggenerated
     */
    private String mchId;

    /**
     * 设备信息
     * @mbggenerated
     */
    private String deviceInfo;

    /**
     * 
     * @mbggenerated
     */
    private String nonceStr;

    /**
     * 签名
     * @mbggenerated
     */
    private String sign;

    /**
     * 结果code
     * @mbggenerated
     */
    private String resultCode;

    /**
     * openid
     * @mbggenerated
     */
    private String openid;

    /**
     * 交易类型
     * @mbggenerated
     */
    private String tradeType;

    /**
     * 总金额
     * @mbggenerated
     */
    private String totalFee;

    /**
     * 交易id
     * @mbggenerated
     */
    private String transactionId;

    /**
     * 商户订单号
     * @mbggenerated
     */
    private String outTradeNo;

    /**
     * 附件
     * @mbggenerated
     */
    private String attach;

    /**
     * 创建时间
     * @mbggenerated
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId == null ? null : mchId.trim();
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo == null ? null : deviceInfo.trim();
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr == null ? null : nonceStr.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode == null ? null : resultCode.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void a(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }
    
    public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        WxPayRecordModel other = (WxPayRecordModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAppid() == null ? other.getAppid() == null : this.getAppid().equals(other.getAppid()))
            && (this.getMchId() == null ? other.getMchId() == null : this.getMchId().equals(other.getMchId()))
            && (this.getDeviceInfo() == null ? other.getDeviceInfo() == null : this.getDeviceInfo().equals(other.getDeviceInfo()))
            && (this.getNonceStr() == null ? other.getNonceStr() == null : this.getNonceStr().equals(other.getNonceStr()))
            && (this.getSign() == null ? other.getSign() == null : this.getSign().equals(other.getSign()))
            && (this.getResultCode() == null ? other.getResultCode() == null : this.getResultCode().equals(other.getResultCode()))
            && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
            && (this.getTradeType() == null ? other.getTradeType() == null : this.getTradeType().equals(other.getTradeType()))
            && (this.getTotalFee() == null ? other.getTotalFee() == null : this.getTotalFee().equals(other.getTotalFee()))
            && (this.getTransactionId() == null ? other.getTransactionId() == null : this.getTransactionId().equals(other.getTransactionId()))
            && (this.getOutTradeNo() == null ? other.getOutTradeNo() == null : this.getOutTradeNo().equals(other.getOutTradeNo()))
            && (this.getAttach() == null ? other.getAttach() == null : this.getAttach().equals(other.getAttach()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAppid() == null) ? 0 : getAppid().hashCode());
        result = prime * result + ((getMchId() == null) ? 0 : getMchId().hashCode());
        result = prime * result + ((getDeviceInfo() == null) ? 0 : getDeviceInfo().hashCode());
        result = prime * result + ((getNonceStr() == null) ? 0 : getNonceStr().hashCode());
        result = prime * result + ((getSign() == null) ? 0 : getSign().hashCode());
        result = prime * result + ((getResultCode() == null) ? 0 : getResultCode().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getTradeType() == null) ? 0 : getTradeType().hashCode());
        result = prime * result + ((getTotalFee() == null) ? 0 : getTotalFee().hashCode());
        result = prime * result + ((getTransactionId() == null) ? 0 : getTransactionId().hashCode());
        result = prime * result + ((getOutTradeNo() == null) ? 0 : getOutTradeNo().hashCode());
        result = prime * result + ((getAttach() == null) ? 0 : getAttach().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appid=").append(appid);
        sb.append(", mchId=").append(mchId);
        sb.append(", deviceInfo=").append(deviceInfo);
        sb.append(", nonceStr=").append(nonceStr);
        sb.append(", sign=").append(sign);
        sb.append(", resultCode=").append(resultCode);
        sb.append(", openid=").append(openid);
        sb.append(", tradeType=").append(tradeType);
        sb.append(", totalFee=").append(totalFee);
        sb.append(", transactionId=").append(transactionId);
        sb.append(", outTradeNo=").append(outTradeNo);
        sb.append(", attach=").append(attach);
        sb.append(", createDate=").append(createDate);
        sb.append("]");
        return sb.toString();
    }
}