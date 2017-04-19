package com.softtek.mdm.model;

import java.util.Date;
/**
 * 设备统计模型
 * @author josen.yang
 *
 */
public class DeviceStatisticsModel {
	private Integer id;					//主键id
	private Date create_time;			//创建时间/分组时间
	private Integer total_device;		//设备总数
	private Integer active_device;		//活跃的用户(7天内登录过的用户)
	private Integer irr_status;			//违规的设备数
	private Integer leave_device;		//托管中的设备数
	private Integer monitor_device;		//监控中的设备数
	private Integer noactivation_device;//待监控设备数
	private Integer new_device;			//新增的设备数
	private Integer delete_device;		//删除设备数
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getTotal_device() {
		return total_device;
	}
	public void setTotal_device(Integer total_device) {
		this.total_device = total_device;
	}
	public Integer getActive_device() {
		return active_device;
	}
	public void setActive_device(Integer active_device) {
		this.active_device = active_device;
	}
	public Integer getIrr_status() {
		return irr_status;
	}
	public void setIrr_status(Integer irr_status) {
		this.irr_status = irr_status;
	}
	public Integer getLeave_device() {
		return leave_device;
	}
	public void setLeave_device(Integer leave_device) {
		this.leave_device = leave_device;
	}
	public Integer getMonitor_device() {
		return monitor_device;
	}
	public void setMonitor_device(Integer monitor_device) {
		this.monitor_device = monitor_device;
	}
	public Integer getNoactivation_device() {
		return noactivation_device;
	}
	public void setNoactivation_device(Integer noactivation_device) {
		this.noactivation_device = noactivation_device;
	}
	public Integer getNew_device() {
		return new_device;
	}
	public void setNew_device(Integer new_device) {
		this.new_device = new_device;
	}
	public Integer getDelete_device() {
		return delete_device;
	}
	public void setDelete_device(Integer delete_device) {
		this.delete_device = delete_device;
	}
	
}
