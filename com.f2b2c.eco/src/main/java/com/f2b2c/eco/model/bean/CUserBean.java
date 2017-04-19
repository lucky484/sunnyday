package com.f2b2c.eco.model.bean;

/**
 * 用户bean
 * @author jane.hui
 *
 */
public class CUserBean {

    /**
     * 主键id
     */
    private Integer id;
    
    /**
     * 账户名称
     */
    private String accountName;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 昵称
     */
    private String nickName;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 注册时间
     */
    private String registerTime;
    
    /**
     * 头像
     */
    private String picUrl;
    
    /**
     * 推荐类型
     */
    private Integer recommendType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(Integer recommendType) {
        this.recommendType = recommendType;
    }
}
