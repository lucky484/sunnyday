
package com.softtek.mdm.service;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PushMobileMsgModel;
import com.softtek.mdm.model.PushMsgHistoryModel;

public interface PushMobileMsgService
{
	Page queryPushMsgListByParams(Map<String, Object> paramMap);
	
	void savePushMobileMsg(PushMobileMsgModel pushMobileMsgModel, List<Integer> deptIdList);

	PushMobileMsgModel queryPushMsgById(Long id);

	PushMobileMsgModel queryPushMsgByMap(Map<String, Object> map);

	void updatePushMobileMsg(PushMobileMsgModel pushMobileMsgModel);

	void deletePushMsgByMap(Map<String, Object> map);
	
	List<PushMobileMsgModel> queryPushMsgByIds(List<Long> picInfoIdList);

	PushMobileMsgModel queryPushMsgByTitle(Map<String, Object> map);

	void savePushHistory(List<PushMsgHistoryModel> pushMsgHistoryModelList);
	
    List<PushMsgHistoryModel> selectPushHistory(String userId, Integer page, Integer pageSize);

    int selectCountPushHistory(String userId);

    Page queryViewMemberByParams(Map<String, Object> map);
    
    /**
     * 分页获取图文消息给客户端
     * @param map
     * |-offSet 当前客户已经保存的图文消息总数
     * |-pageSize 每页记录数目
     * |-userId 用户编号
     * @return 
     */
    Page find(Map<String, Object> map);
}

