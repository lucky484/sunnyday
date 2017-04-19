
package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.PushMobileMsgDao;
import com.softtek.mdm.model.DeptPicMsgRelation;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PushMobileMsgModel;
import com.softtek.mdm.model.PushMsgHistoryModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.PushMobileMsgService;

import jodd.util.StringUtil;

@Service
public class PushMobileMsgServiceImpl implements PushMobileMsgService
{
	@Autowired
	private PushMobileMsgDao pushMobileMsgDao;
	
	@Override
	public Page queryPushMsgListByParams(Map<String, Object> paramMap)
	{
		Page page = new Page();
		Integer count = pushMobileMsgDao.queryPushMsgCount(paramMap);
		List<PushMobileMsgModel> list = (List<PushMobileMsgModel>) pushMobileMsgDao.queryPushMsgListByParams(paramMap);
        // 部门管理员id
        List<Integer> departIdList = new ArrayList<>();
        // 虚拟组Id集合
        List<Integer> virIdList = new ArrayList<>();
        // 用户Id集合
        List<Integer> userList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            departIdList.clear();
            virIdList.clear();
            userList.clear();

            // 部门下的人员获取
            if (StringUtils.isNotBlank(list.get(i).getDepartIds())) {
                String[] deIdArr = (StringUtil.split(list.get(i).getDepartIds(), ","));
                if (deIdArr.length >= 1) {
                    for (int j = 0; j < deIdArr.length; j++) {
                        departIdList.add(Integer.valueOf(deIdArr[j]));
                    }
                    // 查询所有部门下的用户
                    List<Integer> departUids = pushMobileMsgDao.findUserByDepartIdList(departIdList);
                    userList.addAll(departUids);
                }
            }
            // 虚拟组下的人员进行获取
            if (StringUtils.isNotBlank(list.get(i).getVirtualIds())) {
                String[] virArr = (StringUtil.split(list.get(i).getVirtualIds(), ","));
                if (virArr.length >= 1) {
                    for (int j = 0; j < virArr.length; j++) {
                        virIdList.add(Integer.valueOf(virArr[j]));
                    }
                    // 查询所有部门下的用户
                    List<Integer> virUids = pushMobileMsgDao.findUserByVirIdList(virIdList);
                    userList.addAll(virUids);
                }
            }
            if (StringUtils.isNotBlank(list.get(i).getUserIds())) {
                String[] userArr = (StringUtil.split(list.get(i).getUserIds(), ","));
                if (userArr.length >= 1) {
                    for (int j = 0; j < userArr.length; j++) {
                        userList.add(Integer.valueOf(userArr[j]));
                    }
                }
            }
            Set<Integer> testreusername = new HashSet<Integer>(userList);
            list.get(i).setUserCount(testreusername.size());
            list.get(i).setUserIdList(testreusername);
        }

		page.setData(list);
		page.setRecordsFiltered(count);
		page.setRecordsTotal(count);
		return page;
	}

	@Override
	public void savePushMobileMsg(PushMobileMsgModel pushMobileMsgModel, List<Integer> deptIdList)
	{
		pushMobileMsgDao.saveMsg(pushMobileMsgModel);
		if (CollectionUtils.isNotEmpty(deptIdList))
		{
			List<DeptPicMsgRelation> deptPicMsgRelationList = new ArrayList<DeptPicMsgRelation>();
			Integer msgId = pushMobileMsgModel.getId();
			for (Integer dptId : deptIdList)
			{
				DeptPicMsgRelation relation = new DeptPicMsgRelation();
				relation.setOrgManagerId(dptId);
				relation.setPicMsgId(msgId);
				deptPicMsgRelationList.add(relation);
			}
			pushMobileMsgDao.saveDptPicMsgRelation(deptPicMsgRelationList);
		}
	}

	@Override
	public PushMobileMsgModel queryPushMsgById(Long id)
	{
		return pushMobileMsgDao.queryPushMsgById(id);
	}

	@Override
	public PushMobileMsgModel queryPushMsgByMap(Map<String, Object> map) {
        pushMobileMsgDao.updatePushTime(map);
		return pushMobileMsgDao.queryPushMsgByMap(map);
	}

	@Override
	public void updatePushMobileMsg(PushMobileMsgModel pushMobileMsgModel) {
		
		pushMobileMsgDao.updatePushMobileMsg(pushMobileMsgModel);
		
	}

	@Override
	public void deletePushMsgByMap(Map<String, Object> map) {
		pushMobileMsgDao.deletePushMsgByMap(map);
		pushMobileMsgDao.deletePushMsgHistoryByMap(map);
		Integer id = (Integer) map.get("id");
		if (null != id)
		{
			pushMobileMsgDao.deleteDeptBwListRelation(id);
		}
	}
	
	public List<PushMobileMsgModel> queryPushMsgByIds(List<Long> picInfoIdList)
	{
		return pushMobileMsgDao.queryPushMsgByIds(picInfoIdList);
	}

	@Override
	public PushMobileMsgModel queryPushMsgByTitle(Map<String, Object> map) {
		
		return pushMobileMsgDao.queryPushMsgByTitle(map);
	}

	@Override
	public void savePushHistory(List<PushMsgHistoryModel> pushMsgHistoryModelList)
	{
		pushMobileMsgDao.savePushHistory(pushMsgHistoryModelList);
	}

	@Override
    public List<PushMsgHistoryModel> selectPushHistory(String userId, Integer page, Integer pageSize)
	{
        return pushMobileMsgDao.selectPushHistory(userId, page, pageSize);
	}

    @Override
    public int selectCountPushHistory(String userId) {
        return pushMobileMsgDao.selectCountPushHistory(userId);
    }

    @Override
    public Page queryViewMemberByParams(Map<String, Object> paramMap) {
        Page page = new Page();
        PushMobileMsgModel pushMobileMsgModel = pushMobileMsgDao.queryPushMsgByMap(paramMap);
        // 部门管理员id
        List<Integer> departIdList = new ArrayList<>();
        // 虚拟组Id集合
        List<Integer> virIdList = new ArrayList<>();
        // 用户Id集合
        List<Integer> userList = new ArrayList<>();

        // 部门下的人员获取
        if (StringUtils.isNotBlank(pushMobileMsgModel.getDepartIds())) {
            String[] deIdArr = (StringUtil.split(pushMobileMsgModel.getDepartIds(), ","));
            if (deIdArr.length >= 1) {
                for (int j = 0; j < deIdArr.length; j++) {
                    departIdList.add(Integer.valueOf(deIdArr[j]));
                }
                // 查询所有部门下的用户
                List<Integer> departUids = pushMobileMsgDao.findUserByDepartIdList(departIdList);
                userList.addAll(departUids);
            }
        }
        // 虚拟组下的人员进行获取
        if (StringUtils.isNotBlank(pushMobileMsgModel.getVirtualIds())) {
            String[] virArr = (StringUtil.split(pushMobileMsgModel.getVirtualIds(), ","));
            if (virArr.length >= 1) {
                for (int j = 0; j < virArr.length; j++) {
                    virIdList.add(Integer.valueOf(virArr[j]));
                }
                // 查询所有部门下的用户
                List<Integer> virUids = pushMobileMsgDao.findUserByVirIdList(virIdList);
                userList.addAll(virUids);
            }
        }
        if (StringUtils.isNotBlank(pushMobileMsgModel.getUserIds())) {
            String[] userArr = (StringUtil.split(pushMobileMsgModel.getUserIds(), ","));
            if (userArr.length >= 1) {
                for (int j = 0; j < userArr.length; j++) {
                    userList.add(Integer.valueOf(userArr[j]));
                }
            }
        }
        Set<Integer> testreusername = new HashSet<Integer>(userList);
        List<Integer> idlist = new ArrayList<>(testreusername);
        paramMap.put("list", idlist);
        List<UserModel> list = pushMobileMsgDao.findViewMember(paramMap);
        page.setData(list);
        page.setRecordsFiltered(testreusername.size());
        page.setRecordsTotal(testreusername.size());
        return page;
    }

	@Override
	public Page find(Map<String, Object> map) {
		
		List<PushMsgHistoryModel> data=pushMobileMsgDao.find(map);
		int count=pushMobileMsgDao.count(map);
		Page page=new Page(data,count,count);
		return page;
	}
}

