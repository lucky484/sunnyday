package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.MessageSendModel;

public interface MessageSendDao {
   
	public int saveMessage(MessageSendModel messageSend);
	
	public List<MessageSendModel> queryAll(Map<String,Object> map);
	
	public int deleteMessage(List<Integer> list);
	
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
