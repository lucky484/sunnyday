
package com.softtek.mdm.dao;

import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.DeptPicMsgRelation;
import com.softtek.mdm.model.PushMobileMsgModel;
import com.softtek.mdm.model.PushMsgHistoryModel;
import com.softtek.mdm.model.UserModel;

/**
 * 推送消息dao date: Apr 22, 2016 7:16:00 PM
 *
 * @author brave.chen
 */
public interface PushMobileMsgDao
{

	Integer queryPushMsgCount(Map<String, Object> paramMap);

	List<PushMobileMsgModel> queryPushMsgListByParams(Map<String, Object> paramMap);

	void saveMsg(PushMobileMsgModel pushMobileMsgModel);

	PushMobileMsgModel queryPushMsgById(Long id);

	PushMobileMsgModel queryPushMsgByMap(Map<String, Object> map);

	void updatePushMobileMsg(PushMobileMsgModel pushMobileMsgModel);

	void deletePushMsgByMap(Map<String, Object> map);
	
	List<PushMobileMsgModel> queryPushMsgByIds(List<Long> idList);

	PushMobileMsgModel queryPushMsgByTitle(Map<String, Object> map);

	void savePushHistory(List<PushMsgHistoryModel> pushMsgHistoryModelList);
	
    List<PushMsgHistoryModel> selectPushHistory(String userId, Integer page, Integer pageSize);

    /**
     * 分页获取图文消息给客户端
     * @param map
     * |-offSet 当前客户已经保存的图文消息总数
     * |-pageSize 每页记录数目
     * |-userId 用户编号
     * @return 
     */
    List<PushMsgHistoryModel> find(Map<String, Object> map);
    /**
     * 分页获取图文消息给客户端
     * @param map
     * |-offSet 当前客户已经保存的图文消息总数 ——可无
     * |-pageSize 每页记录数目 ——可无
     * |-userId 用户编号
     * @return 
     */
    int count(Map<String, Object> map);
    
	void deletePushMsgHistoryByMap(Map<String, Object> map);

	void saveDptPicMsgRelation(List<DeptPicMsgRelation> deptPicMsgRelationList);
	
	List<DeptPicMsgRelation> queryDptPicMsgRelation(List<Integer> deptIdList);

	void deleteDeptBwListRelation(Integer pushMsgId);

    int selectCountPushHistory(String userId);

    List<Integer> findUserByDepartIdList(List<Integer> list);

    List<Integer> findUserByVirIdList(List<Integer> list);

    List<UserModel> findViewMember(Map<String, Object> paramMap);

    void updatePushTime(Map<String, Object> map);
}

