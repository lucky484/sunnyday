package com.f2b2c.eco.model.market;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户积分操作记录Model
 * @author jane.hui
 *
 */
public class CUserScoreRecordModel implements Serializable {
    /**
     * 主键Id
     * @mbggenerated
     */
    private Integer id;

    /**
     * 操作类型(1.充值 2.消费 3.获得积分)
     * @mbggenerated
     */
    private String operateType;

    /**
     * 操作内容
     * @mbggenerated
     */
    private String operateContent;

    /**
     * 用户积分表外键
     * @mbggenerated
     */
    private Integer cUserScoreId;

    /**
     * 标识[1.增加(+)  2.减去(-)]
     * @mbggenerated
     */
    private String tag;

    /**
     * 积分
     * @mbggenerated
     */
    private Integer score;

    /**
     * 创建时间
     * @mbggenerated
     */
    private Date createTime;

    /**
     * String类型创建时间字符串
     */
    private String strCreateDate;
    
    /**
     * 序列化
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType == null ? null : operateType.trim();
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent == null ? null : operateContent.trim();
    }

    public Integer getcUserScoreId() {
        return cUserScoreId;
    }

    public void setcUserScoreId(Integer cUserScoreId) {
        this.cUserScoreId = cUserScoreId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }
    
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStrCreateDate() {
        return strCreateDate;
    }

    public void setStrCreateDate(String strCreateDate) {
        this.strCreateDate = strCreateDate;
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
        CUserScoreRecordModel other = (CUserScoreRecordModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOperateType() == null ? other.getOperateType() == null : this.getOperateType().equals(other.getOperateType()))
            && (this.getOperateContent() == null ? other.getOperateContent() == null : this.getOperateContent().equals(other.getOperateContent()))
            && (this.getcUserScoreId() == null ? other.getcUserScoreId() == null : this.getcUserScoreId().equals(other.getcUserScoreId()))
            && (this.getTag() == null ? other.getTag() == null : this.getTag().equals(other.getTag()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOperateType() == null) ? 0 : getOperateType().hashCode());
        result = prime * result + ((getOperateContent() == null) ? 0 : getOperateContent().hashCode());
        result = prime * result + ((getcUserScoreId() == null) ? 0 : getcUserScoreId().hashCode());
        result = prime * result + ((getTag() == null) ? 0 : getTag().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", operateType=").append(operateType);
        sb.append(", operateContent=").append(operateContent);
        sb.append(", cUserScoreId=").append(cUserScoreId);
        sb.append(", tag=").append(tag);
        sb.append(", score=").append(score);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}