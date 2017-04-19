package com.f2b2c.eco.model.platform;

import java.util.Date;

/**
 * 
 * @author mozzie.chu
 *
 */
public class FHelpModel {

	/**
	 * 问题ID
	 */
	private Integer id;
	
	/**
	 * 问题
	 */
	private String question;
	
	/**
	 * 回答/答案
	 */
	private String answer;
	
	/**
	 * type(一级内容)
	 * 0:购买咨询 | 1:支付问题 | 2:物流与售后 | 3:其他问题 | 4:问答详情
	 */
	private String type;
	
	/**
	 * status(二级内容)
	 * 0-0:购买咨询，0-1:购买相关问题 |
	 * 1-0:支付问题，1-1:支付相关，1-2:积分卡券使用，1-3:支付异常 |
	 * 2-0:物流与售后，2-1:物流配送，2-2:售后咨询 |
	 * 3-0:其它问题，3-1:帐号与密码 |
	 * 4-0:问答详情 |
	 */
	private String status;
	
	/**
	 * 创建时间
	 */
	private Date createdTime;
	
	/**
	 * 修改时间
	 */
	private Date updatedTime;
	
	/**
	 * 图片路径
	 */
	private String pic;

	/**
	 * 是否热点问题
	 * 0:否，1:是
	 */
	private String style;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
}
