package com.softtek.pst.model;

import java.util.Date;

public class LoggingModel {
	/**
	 * 日志ID
	 */
	private long loggingId;
	/**
	 * 操作时间
	 */
	private Date createTime;
	/**
	 * 操作
	 */
	private String action;
	/**
	 * 操作者
	 */
	private String operator;
	/**
	 * 用户ID
	 */
	private long userId;
	/**
	 * 事件类型
	 */
	private int eventType;
	/**
	 * 事件名称
	 */
	private String eventName;
	/**
	 * 被操作的数据库表名
	 */
	private String tables;

	public  LoggingModel() {

	}

	public LoggingModel(long loggingId, Date createTime, String action,
			String operator, long userId, int eventType, String eventName,
			String tables) {
		this.loggingId = loggingId;
		this.createTime = createTime;
		this.action = action;
		this.operator = operator;
		this.userId = userId;
		this.eventType = eventType;
		this.eventName = eventName;
		this.tables = tables;
	}

	/**
	 * @return the loggingId
	 */
	public long getLoggingId() {
		return loggingId;
	}

	/**
	 * @param loggingId the loggingId to set
	 */
	public void setLoggingId(long loggingId) {
		this.loggingId = loggingId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the eventType
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * @param i the eventType to set
	 */
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the tables
	 */
	public String getTables() {
		return tables;
	}

	/**
	 * @param tables the tables to set
	 */
	public void setTables(String tables) {
		this.tables = tables;
	}
	
	
}
