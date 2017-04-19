package com.softtek.mdm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.DeviceLegalListDao;
import com.softtek.mdm.dao.MessageSendDao;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.dao.UserVirtualGroupDao;
import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.MessageSendModel;
import com.softtek.mdm.model.PushMobileMsgModel;
import com.softtek.mdm.service.MessageSendService;
import com.softtek.mdm.service.PushMobileMsgService;
import com.softtek.mdm.util.Constant;
import com.softtek.mdm.util.Constant.SplitSymbol;

@Service
public class MessageSendServiceImpl implements MessageSendService{
	
	private DeviceLegalListDao deviceLegalListDao;
    
	@Autowired
	private MessageSendDao messageSendDao;
	
	@Autowired
	private PushMobileMsgService pushMobileMsgService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserVirtualGroupDao UserVirtualGroupDao;
	
	@Override
	public int saveMessage(MessageSendModel messageSend) {
		
		return messageSendDao.saveMessage(messageSend);
	}

	@Override
	public List<MessageSendModel> queryAll(Integer deviceId,Integer userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("deviceId",deviceId);
		map.put("userId",userId);
		return messageSendDao.queryAll(map);
	}

	@Override
	public int deleteMessage(List<Integer> list) {
		
		return messageSendDao.deleteMessage(list);
	}
	
	
	public MessageSendModel generateMessageSendModel(String id, Integer orgId, ManagerModel managerModel)
	{
		MessageSendModel pMsgModel = new MessageSendModel();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("id", id);
		PushMobileMsgModel model = pushMobileMsgService.queryPushMsgByMap(map);
		
		if (null != model)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			pMsgModel.setMessage_title(model.getMsgTheme());
			pMsgModel.setMessage(model.getMsgTheme());
			pMsgModel.setCreateBy(managerModel.getId());
			pMsgModel.setCreateTime(new Date());
			pMsgModel.setUpdateTime(new Date());
			pMsgModel.setUpdateBy(managerModel.getId());
			pMsgModel.setName(managerModel.getName());
			pMsgModel.setCreateDateStr(sdf.format(new Date()));
			pMsgModel.setMsgId(String.valueOf(model.getId()));
			
			String imgUrls = StringUtils.trimToEmpty(model.getPicUrl());
			String[] imgUrlArr = imgUrls.split(Constant.SplitSymbol.COMMA_SYMBOL);
			pMsgModel.setImgUrl(imgUrlArr[0]);
		}
		return pMsgModel;
	}
 
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getUserIdListById(String id, Integer orgId)
	{
		List<Integer> allList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("id", id);
		PushMobileMsgModel pushMobileMsgModel = pushMobileMsgService.queryPushMsgByMap(map);
		if (null != pushMobileMsgModel)
		{
			String deptIds = pushMobileMsgModel.getDepartIds();
			
			List<Integer> dptUserIds = getUserIdsByDeptIds(deptIds, orgId);
			
			String virtualIds = pushMobileMsgModel.getVirtualIds();
			
			List<Integer> virtualUserIds = getUserIdsByVirtualIds(virtualIds);
			
			allList = (List<Integer>) CollectionUtils.union(dptUserIds, virtualUserIds);
			String userIds = pushMobileMsgModel.getUserIds();
			
			List<Integer> userIdList = new ArrayList<Integer>();
			if (StringUtils.isNotEmpty(userIds))
			{
				String [] userArr = userIds.split(SplitSymbol.COMMA_SYMBOL);
				
				for (int i = 0; i < userArr.length; i ++)
				{
					userIdList.add(Integer.valueOf(userArr[i]));
				}
			}
			
			if (CollectionUtils.isEmpty(allList))
			{
				allList = userIdList;
			}
			
			else
			{
				allList = (List<Integer>) CollectionUtils.union(allList, userIdList);
			}
		}
		return allList;
	}
	
	private List<Integer> getUserIdsByDeptIds(String deptIds, Integer orgId)
	{
		List<Integer> userList = new ArrayList<Integer>();
		
		if (StringUtils.isNotBlank(deptIds))
		{
			Map<String, Object> map = new HashMap<String, Object>();
			String [] deptArr = deptIds.split(SplitSymbol.COMMA_SYMBOL);
			List<Integer> deptIdList = new ArrayList<Integer>();
			for (int i = 0; i < deptArr.length; i++)
			{
				deptIdList.add(Integer.valueOf(deptArr[i]));
			}
			
			if (CollectionUtils.isNotEmpty(deptIdList))
			{
				map.put("deptIdList", deptIdList);
				map.put("orgId", orgId);
				userList = userDao.getUserIdsByDeptIds(map);
			}
		}
		
		return userList;
	}
	
	private List<Integer> getUserIdsByVirtualIds(String virtualIds)
	{
		List<Integer> userList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(virtualIds))
		{
			Map<String, Object> map = new HashMap<String, Object>();
			String [] virtualArr = virtualIds.split(SplitSymbol.COMMA_SYMBOL);
			
			List<Integer> virtualIdList = new ArrayList<Integer>();
			for (int i = 0; i < virtualArr.length; i++)
			{
				virtualIdList.add(Integer.valueOf(virtualArr[i]));
			}
			
			if (CollectionUtils.isNotEmpty(virtualIdList))
			{
				map.put("virtualIdList", virtualIdList);
				userList = UserVirtualGroupDao.getUserIdsByVirtualIdList(map);
			}
		}
		
		return userList;
	}

	@Override
	public List<MessageSendModel> find(Map<String, Object> map) {
		return messageSendDao.find(map);
	}
}
