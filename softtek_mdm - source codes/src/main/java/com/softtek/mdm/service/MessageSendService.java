package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.MessageSendModel;

public interface MessageSendService {
   
	int saveMessage(MessageSendModel messageSend);
	
	List<MessageSendModel> queryAll(Integer deviceId,Integer userId);
	
	int deleteMessage(List<Integer> list);
	
	/**
	 * 获取用户id列表
	 *
	 * @author brave.chen
	 * @param id 图文消息id
	 * @param orgId 组织机构id
	 * @return 用户id列表
	 */
	List<Integer> getUserIdListById(String id, Integer orgId);
	
	/**
	 * 查询系统消息内容
	 * @param map
	 * |-deviceId 设备id
	 * |-userId 用户id
	 * |-lastDate 上次最后一条消息的时间yyyy/MM/dd HH:mm:ss
	 * @return 
	 */
	List<MessageSendModel> find(Map<String, Object> map);
}
