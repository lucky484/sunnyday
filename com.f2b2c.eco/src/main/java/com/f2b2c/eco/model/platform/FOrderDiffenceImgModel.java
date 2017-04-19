package com.f2b2c.eco.model.platform;

import java.io.Serializable;

/**
 * 差价图片
 * @author jane.hui
 *
 */
public class FOrderDiffenceImgModel implements Serializable {
    /**
     * 差价主键
     */
    private String id;

    /**
     * 差价订单外键
     */
    private String diffenceId;

    /**
     * 图片地址
     * @mbggenerated
     */
    private String imgUrl;

    /**
     * 序列号
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDiffenceId() {
        return diffenceId;
    }

    public void setDiffenceId(String diffenceId) {
        this.diffenceId = diffenceId == null ? null : diffenceId.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
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
        FOrderDiffenceImgModel other = (FOrderDiffenceImgModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDiffenceId() == null ? other.getDiffenceId() == null : this.getDiffenceId().equals(other.getDiffenceId()))
            && (this.getImgUrl() == null ? other.getImgUrl() == null : this.getImgUrl().equals(other.getImgUrl()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDiffenceId() == null) ? 0 : getDiffenceId().hashCode());
        result = prime * result + ((getImgUrl() == null) ? 0 : getImgUrl().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", diffenceId=").append(diffenceId);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append("]");
        return sb.toString();
    }
}