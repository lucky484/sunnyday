package com.softtek.mdm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.softtek.mdm.dao.PolicyDao;
import com.softtek.mdm.dao.StructureDao;
import com.softtek.mdm.dao.UserDao;
import com.softtek.mdm.model.Page;
import com.softtek.mdm.model.PolicyModel;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.StructureService;
import com.softtek.mdm.thread.MqProducerThread;
import com.softtek.mdm.util.IosPushUtil;

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	private PolicyDao policyDao;
	
	@Autowired
	private StructureDao structureDao;
	
    @Autowired
    private UserDao userDao;
    
	@Autowired
	private StructureService structureService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
    /**
     * 新增策略,应用到部门和用户
     */
	@Override
	public int save(PolicyModel policy,String id,List<StructureModel> strList) {
		StructureModel structure = new StructureModel();
		Map<String,String> map = new HashMap<String,String>();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		Map<String,Object> userMap = new HashMap<String, Object>();
		List<StructureModel> idList = new ArrayList<StructureModel>();
		List<StructureModel> structList = new ArrayList<StructureModel>();
		List<String> deviceToken = new ArrayList<String>();
		List<Integer> idsList = new ArrayList<Integer>();
		//存放一级部门和他的下级部门
		List<Integer> topList = new ArrayList<Integer>(); 
		
		UserModel user = new UserModel();
		user.setPolicy(policy);
		
		//默认策略的id
		Integer defaultPolicyId = policyDao.queryDefaultPolicy(policy.getOrganization().getId()); 
		paramMap.put("policyId",defaultPolicyId);
		paramMap.put("orgId",policy.getOrganization().getId());
		//查询出所有应用默认策略的一级部门
		List<Integer> dptList = structureDao.queryDepartmentByDefaultPolicy(paramMap);  
		int result = 0;
		if(policy.getIsDefault() ==(Integer) 1){
			policyDao.updateDefaultPolicy(policy.getOrganization().getId());
			result = policyDao.save(policy);
		    if(CollectionUtils.isNotEmpty(dptList)){
		    	for(Integer dptId :dptList){  //遍历所有的一级部门，找出所有的子部门
		    		structureService.queryAllChildrenPolicyId(dptId,strList,structList); //找出当前部门的所有下级部门
		    		if(CollectionUtils.isNotEmpty(structList)){
		    			for(StructureModel s : structList){
		    				if(Integer.valueOf(defaultPolicyId).equals(s.getPolicy().getId())){
		    					topList.add(s.getId());  //找出所有继承上级策略的子部门
		    				}
		    			}
		    		}
		    	}
		    	topList.addAll(dptList);//将所有的一级部门放到list中
		    	for(int m=0;m<topList.size()-1;m++){
					for(int j=topList.size()-1;j>m;j--){
						if(topList.get(j).equals(topList.get(m))){ 
							topList.remove(j); 
					      } 
					}
				}
		    }
		}else{
			result = policyDao.save(policy);
		}
		if(result == 1){
			if(StringUtils.isNotEmpty(id)){
				String[] ids = id.split(",");
				structure.setPolicy(policy);
				for(int i=0;i<ids.length;i++){
					idList = new ArrayList<StructureModel>();
					Integer policyId = structureDao.queryPolicyIdById(Integer.valueOf(ids[i]));  //后来选中的部门都要找出修改之前的策略id
					structureService.queryAllChildrenPolicyId(Integer.valueOf(ids[i]),strList,idList); //找出当前部门的所有下级部门
					for(StructureModel str : idList){
						if(Integer.valueOf(policyId).equals(str.getPolicy().getId())){
							idsList.add(str.getId());  //遍历所有的部门，取出所有下级部门和上级部门策略一样的部门id
						}
					}
					idsList.add(Integer.valueOf(ids[i]));
					idsList.removeAll(topList);
					idsList.addAll(topList);
				}
				for(int m=0;m<idsList.size()-1;m++){
					for(int j=idsList.size()-1;j>m;j--){
						if(idsList.get(j).equals(idsList.get(m))){ 
							idsList.remove(j); 
					      } 
					}
				}
				int count = structureService.updatePolicyByList(policy.getId(),idsList);//修改部的策略
				user.setStructure(structure);
				if(count == idsList.size()){
					for(Integer in : idsList){
						//修改完部门的策略后，在修改部门下的人的策略
						structure.setId(in);
						user.setPolicy(policy);
						user.setStructure(structure);
						userDao.updatePolicyById(user);
					}
					map.put("policy", JSONObject.fromObject(policy).toString());
					userMap.put("idList", idsList);
					List<PolicyModel> userList = policyDao.queryUserIdbyDepartmentId(userMap);
					map.put("policy", JSONObject.fromObject(policy).toString());
					for(PolicyModel p : userList){
						if(p.getDeviceManager().getDevice_type() != null){
							if(p.getDeviceManager().getDevice_type().equals("android")){
								MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(p.getUser().getId()), null, null, 2, map);
								taskExecutor.execute(mqProducerThread);
							}else if(p.getDeviceManager().getDevice_type().equals("ios")){
								if(p.getDeviceManager().getDeviceToken() != null && !p.getDeviceManager().getDeviceToken().equals("")){
									deviceToken.add(p.getDeviceManager().getDeviceToken().replaceAll(" ",""));
								}
							}
						}
					}
					if(deviceToken.size() > 0){
						IosPushUtil.pushDataToIos(deviceToken,null,1,"",map);  //多个ios设备放到队列里面推送
					}
				}
			}
		}
		return result;
	}


	@Override
	public Page queryAll(Integer begin, Integer num,Integer orgId) {
		Page p = new Page();
		int total = policyDao.queryAllCount(orgId);
		p.setData(policyDao.queryAll(begin, num,orgId)); 
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p;
	}

	@Override
	public int deletePolicy(Integer id,List<StructureModel> strList,Integer orgId) {
		List<StructureModel> list = structureDao.quertAllDepartmentByPolicyId(id);
		List<StructureModel> idList = new ArrayList<StructureModel>();
		List<String> deviceToken = new ArrayList<String>();
		List<Integer> idsList = null;
		UserModel user = new UserModel();
		//修改部门的策略
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getParent() != null){
						StructureModel ste = getParent(list,list.get(i).getParent().getId());//找出list中的数据是否有上下级的关系，如果ste不是null，则说明在list中存在当前数据的上级
						//这里只修改下级部门，不修改当前部门
						if(ste == null){                                              
							Map<String,String> map = new HashMap<String, String>();
							idsList = new ArrayList<Integer>();
							//只修改当前部门的策略，不修改下级部门的策略
							String parentId = structureDao.queryParentByparentId(Integer.valueOf(list.get(i).getId()));
							Integer policyId = null;
							if(parentId != null && !"".equals(parentId)){
								policyId = structureDao.queryParentPolicyById(Integer.valueOf(list.get(i).getId()));
							}else{
								//当前部门为一级部门，所以要修改为默认策略
								policyId = policyDao.queryDefaultPolicy(orgId);
							}
							structureService.queryAllChildrenPolicyId(Integer.valueOf(list.get(i).getId()),strList,idList); //找出当前部门的所有下级部门
							for(StructureModel str : idList){
								if(Integer.valueOf(id).equals(str.getPolicy().getId())){
									idsList.add(str.getId());  //遍历所有的下级部门，取出所有下级部门和上级部门策略一样的部门id
								}
							}
						     Iterator<StructureModel> iter = list.iterator();
							 while(iter.hasNext()){
								 if(idsList.contains(iter.next().getId())){
									 iter.remove();
								 }
							 }
							idsList.add(Integer.valueOf(list.get(i).getId())); //把当前部门放到list中一起修改
							//根据策略id查询策略的详细信息
							PolicyModel p = policyDao.queryAllInfoById(policyId);
							//修改策略
							int count = structureService.updatePolicyByList(policyId,idsList);
							if(count == idsList.size()){
								for(Integer j : idsList){
									list.get(i).setId(j);
									user.setPolicy(p);
									user.setStructure(list.get(i));
									userDao.updatePolicyById(user);
								}
							}
							Map<String,Object> userMap = new HashMap<String, Object>();
							userMap.put("idList",idsList);
							List<PolicyModel> userList = policyDao.queryUserIdbyDepartmentId(userMap);
							map.put("policy", JSONObject.fromObject(p).toString());
							for(PolicyModel policy : userList){
								if(policy.getDeviceManager().getDevice_type() != null){
									if(policy.getDeviceManager().getDevice_type().equals("android")){
										MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(policy.getUser().getId()), null, null, 2, map);
										taskExecutor.execute(mqProducerThread);
									}else if(policy.getDeviceManager().getDevice_type().equals("ios")){
										deviceToken.add(policy.getDeviceManager().getDeviceToken().replaceAll(" ",""));
									}
								}
							}
							if(deviceToken.size() > 0){
								IosPushUtil.pushDataToIos(deviceToken,null,1,"",map);
							}
						}
			     }
			}
		}
		//删除策略
		return policyDao.deletePolicy(id);
	}
    //比较出list中有上级的数据
	private StructureModel getParent(List<StructureModel> list,Integer id){
		if(list != null && list.size() > 1){
			for(StructureModel str : list){
				if(str.getId().equals(id)){
					return str;
				}
			}
		}
		return null;
	}
	
	@Override
	public PolicyModel findOne(Integer id) {
		return policyDao.findOne(id);
	}

	@Override
	public PolicyModel queryInfoById(Integer id) {

		return policyDao.queryInfoById(id);
	}

	/**
	 * 修改策略,同时应用到部门，和用户
	 */
	public int updatePolicyInfo(PolicyModel policy,String ids,String departmentIds,List<StructureModel> strList) {
        UserModel user = new UserModel();
        StructureModel structure = new StructureModel();
//        ExecutorService pool = Executors.newFixedThreadPool(100);
		Map<String,Object> paramMap = new HashMap<String, Object>();
        List<StructureModel> structList = new ArrayList<StructureModel>();
        List<StructureModel> idList = null;
    	List<Integer> topList = new ArrayList<Integer>(); //存放一级部门和他的下级部门
        List<Integer> idsList = null;
        List<String> deviceToken = null;
        Integer defaultPolicyId = policyDao.queryDefaultPolicy(policy.getOrganization().getId()); //默认策略的id
        paramMap.put("policyId",defaultPolicyId);
		paramMap.put("orgId",policy.getOrganization().getId());
		List<Integer> dptList = structureDao.queryDepartmentByDefaultPolicy(paramMap);  //查询出所有应用默认策略的一级部门
		Map<String,String> map = new HashMap<String, String>();
		if(policy.getIsDefault() == 1){
			//如果这条策略修改为默认策略，把之前所有的策略都修改为非默认策略
			policyDao.updateDefaultPolicy(policy.getOrganization().getId());
			if(dptList != null && dptList.size() > 0){
				deviceToken = new ArrayList<String>();
				for(Integer dptId :dptList){  //遍历所有的一级部门，找出所有的子部门
					structureService.queryAllChildrenPolicyId(dptId,strList,structList); //找出当前部门的所有下级部门
					if(structList != null && structList.size() > 0){
						for(StructureModel s : structList){
							if(Integer.valueOf(defaultPolicyId).equals(s.getPolicy().getId())){
								topList.add(s.getId());  //找出所有继承上级策略的子部门
							}
						}
					}
				}
				topList.addAll(dptList);//将所有的一级部门放到list中
				for(int m=0;m<topList.size()-1;m++){
					for(int j=topList.size()-1;j>m;j--){
						if(topList.get(j).equals(topList.get(m)))   { 
							topList.remove(j); 
					      } 
					}
				}
				int count = structureService.updatePolicyByList(policy.getId(),topList);//修改部的策略
				if(count == topList.size()){
					for(Integer in : topList){
						//修改完部门的策略后，在修改部门下的人的策略
						structure.setId(in);
						user.setPolicy(policy);
						user.setStructure(structure);
						userDao.updatePolicyById(user);
					}
					Map<String,Object> userMap = new HashMap<String, Object>();
					userMap.put("idList",topList);
					List<PolicyModel> userList = policyDao.queryUserIdbyDepartmentId(userMap);
					map.put("policy", JSONObject.fromObject(policy).toString());
					for(PolicyModel p : userList){
						if(p.getDeviceManager().getDevice_type() != null){
							if(p.getDeviceManager().getDevice_type().equals("android")){
								MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(p.getUser().getId()), null, null, 2, map);
								taskExecutor.execute(mqProducerThread);
							}else if(p.getDeviceManager().getDevice_type().equals("ios")){
								deviceToken.add(p.getDeviceManager().getDeviceToken().replaceAll(" ",""));
							}
						}
					}
					if(deviceToken.size() > 0){
						IosPushUtil.pushDataToIos(deviceToken,null,1,"",map);
					}
				}
			}
		}
		//第一次修改策略
		int result = policyDao.updatePolicyInfo(policy);
		if(ids != null && !"".equals(ids)){ 
			idsList = new ArrayList<Integer>();
			structure.setPolicy(policy);
			deviceToken = new ArrayList<String>();
			//修改部门应用的策略
			String[] id = ids.split(",");
			if(departmentIds != null && !"".equals(departmentIds)){
				String[] department = departmentIds.split(",");
				Map<String,List<Integer>> map1 = compare(id,department); //去除两个数组的重复元素
				List<Integer> secList = (List<Integer>) map1.get("list3");
				if(secList != null && secList.size() > 0){
					for(Integer s : secList){
						idList = new ArrayList<StructureModel>();
//						idsList = new ArrayList<Integer>();
						Integer policyId = structureDao.queryPolicyIdById(s);  //后来选中的部门都要找出修改策略之前的策略id
						structureService.queryAllChildrenPolicyId(Integer.valueOf(s),strList,idList); //找出当前部门的所有下级部门
						for(StructureModel str : idList){
							if(Integer.valueOf(policyId).equals(str.getPolicy().getId())){
								idsList.add(str.getId());  //遍历所有的下级部门，取出所有下级部门和上级部门策略一样的部门id
							}
						}
						idsList.add(Integer.valueOf(s));
						structure.setId(Integer.valueOf(s));
						int count = structureService.updatePolicyByList(policy.getId(),idsList);
						if(count == idsList.size()){
							for(Integer i : idsList){
								//修改完部门的策略后，在修改部门下的人的策略
								structure.setId(i);
								user.setPolicy(policy);
								user.setStructure(structure);
								userDao.updatePolicyById(user);
							}
							Map<String,Object> userMap = new HashMap<String, Object>();
							userMap.put("idList",idsList);
							List<PolicyModel> userList = policyDao.queryUserIdbyDepartmentId(userMap);
							map.put("policy", JSONObject.fromObject(policy).toString());
							for(PolicyModel p : userList){
								if(p.getDeviceManager().getDevice_type() != null){
									if(p.getDeviceManager().getDevice_type().equals("android")){
										MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(p.getUser().getId()), null, null, 2, map);
										taskExecutor.execute(mqProducerThread);
									}else if(p.getDeviceManager().getDevice_type().equals("ios")){
										deviceToken.add(p.getDeviceManager().getDeviceToken().replaceAll(" ",""));
									}
								}
							}
							if(deviceToken.size() > 0){
								IosPushUtil.pushDataToIos(deviceToken,null,1,"",map);
							}
						}
					}
				}
/*				else{
					List<Integer> stcList = new ArrayList<Integer>();
					List<String> departmentList = Arrays.asList(id);
					for(String s : department){
						if(!departmentList.contains(s)){
							stcList.add(Integer.valueOf(s));
						}
					}
					for(int m=0;m<stcList.size();m++){
						idList = new ArrayList<StructureModel>();
						String parentId = structureDao.queryParentByparentId(stcList.get(m));
						structure.setId(stcList.get(m));
						Integer policyId = null;
						if(parentId != null && !parentId.equals("")){
							//如果不是一级部门，则查询上级部门应用的策略，应用上级部门的策略
							policyId = structureDao.queryPolicyById(structure);
						}else{
							policyId = policyDao.queryDefaultPolicy(policy.getOrganization().getId());
						}
						structureService.queryAllChildrenPolicyId(stcList.get(m),strList,idList); //找出当前部门的所有下级部门
						for(StructureModel str : idList){
							if(policy.getId().equals(str.getPolicy().getId())){
								idsList.add(str.getId());  //遍历所有的下级部门，取出所有下级部门和上级部门策略一样的部门id
							}
						}
						stcList.removeAll(idsList);
						idsList.add(stcList.get(m));
						structure.setId(stcList.get(m));
						PolicyModel p = policyDao.queryAllInfoById(policyId);
						int count = structureService.updatePolicyByList(policyId,idsList);
						if(count == idsList.size()){
							for(Integer i : idsList){
								//修改完部门的策略后，在修改部门下的人的策略
								structure.setId(i);
								user.setPolicy(p);
								user.setStructure(structure);
								userDao.updatePolicyById(user);
							}
							Map<String,Object> userMap = new HashMap<String, Object>();
							userMap.put("idList",idsList);
							List<PolicyModel> userList = policyDao.queryUserIdbyDepartmentId(userMap);
							map.put("policy", JSONObject.fromObject(p).toString());
							for(PolicyModel po : userList){
								if(po.getDeviceManager().getDevice_type() != null){
									if(po.getDeviceManager().getDevice_type().equals("andorid")){
										MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(po.getUser().getId()), null, null, 2, map);
										taskExecutor.execute(mqProducerThread);
									}else if(po.getDeviceManager().getDevice_type().equals("ios")){
										deviceToken.add(po.getDeviceManager().getDeviceToken().replaceAll(" ",""));
									}
								}
							}
							if(deviceToken.size() > 0){
								IosPushUtil.pushDataToIos(deviceToken,null,1,"",map);
							}
						}
					}
				}*/
			}else{
				for(int i=0;i<id.length;i++){
					idList = new ArrayList<StructureModel>();
					idsList = new ArrayList<Integer>();
					//查询当前部门在修改之前的策略id
					Integer policyId = structureDao.queryPolicyIdById(Integer.valueOf(id[i]));
					structureService.queryAllChildrenPolicyId(Integer.valueOf(id[i]),strList,idList); //找出当前部门的所有下级部门
					for(StructureModel str : idList){
						if(Integer.valueOf(policyId).equals(str.getPolicy().getId())){
							idsList.add(str.getId());  //遍历所有的下级部门，取出所有下级部门和上级部门策略一样的id
						}
					}
					idsList.add(Integer.valueOf(id[i]));
//					structure.setId(Integer.valueOf(s));
					for(int m=0;m<idsList.size()-1;m++){
						for(int j=idsList.size()-1;j>m;j--){
							if(idsList.get(j).equals(idsList.get(m)))   { 
								idsList.remove(j); 
						      } 
						}
					}
					int count = structureService.updatePolicyByList(policy.getId(),idsList);
					if(count == idsList.size()){
						for(Integer in : idsList){
							//修改完部门的策略后，在修改部门下的人的策略
							structure.setId(in);
							user.setPolicy(policy);
							user.setStructure(structure);
							userDao.updatePolicyById(user);
						}
						Map<String,Object> userMap = new HashMap<String, Object>();
						userMap.put("idList",idsList);
						List<PolicyModel> userList = policyDao.queryUserIdbyDepartmentId(userMap);
						map.put("policy", JSONObject.fromObject(policy).toString());
						for(PolicyModel p : userList){
							if(p.getDeviceManager().getDevice_type() != null){
								if(p.getDeviceManager().getDevice_type().equals("android")){
									MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(p.getUser().getId()), null, null, 2, map);
									taskExecutor.execute(mqProducerThread);
								}else if(p.getDeviceManager().getDevice_type().equals("ios")){
									deviceToken.add(p.getDeviceManager().getDeviceToken().replaceAll(" ",""));
								}
							}
						}
						if(deviceToken.size() > 0){
							IosPushUtil.pushDataToIos(deviceToken,null,1,"",map);
						}
					}
				}
			}
		}
		//第二次修改
		if(departmentIds != null && !"".equals(departmentIds)){
//	        Map<String,String> map = new HashMap<String, String>();
			String[] departmentId = departmentIds.split(",");
			List<Integer> departmentIdList = new ArrayList<Integer>();
			for(int m=0;m<departmentId.length;m++){
				departmentIdList.add(Integer.valueOf(departmentId[m]));
			}
			if(ids != null && !"".equals(ids)){
				idsList = new ArrayList<Integer>();
				String[] secId = ids.split(",");
				Map<String,List<Integer>> map2 = compare(secId,departmentId);
				List<Integer> list = (List<Integer>) map2.get("list4");
				//找出两个数组中不一样的数据,存入list中
				if(list != null && list.size() > 0){
					for(int i=0;i<list.size();i++){
						idList = new ArrayList<StructureModel>();
						Integer idStr = queryParentId(list,list.get(i));
						if(idStr != null){
							structure.setId(idStr);
							String parentId = structureDao.queryParentByparentId(idStr);
							Integer policyId = null;
							if(parentId != null && !parentId.equals("")){
								//如果不是一级部门，则查询上级部门应用的策略，应用上级部门的策略
								policyId = structureDao.queryPolicyById(structure);
							}else{
								policyId = policyDao.queryDefaultPolicy(policy.getOrganization().getId());
							}
							structureService.queryAllChildrenPolicyId(idStr,strList,idList); //找出当前部门的所有下级部门
							for(StructureModel str : idList){
								if(Integer.valueOf(policy.getId()).equals(str.getPolicy().getId())){
									idsList.add(str.getId());  //遍历所有的下级部门，取出所有下级部门和上级部门策略一样的id
								}
							}
							list.removeAll(idsList);
							idsList.add(idStr);
							PolicyModel p = policyDao.queryAllInfoById(policyId);
							int count = structureService.updatePolicyByList(policyId,idsList);
							//把修改之前的策略修改为继承上级策略
							if(count == idsList.size()){
								deviceToken = new ArrayList<String>();
								for(Integer in : idsList){
									//修改完部门的策略后，在修改部门下的人的策略
									structure.setId(in);
									user.setPolicy(p);
									user.setStructure(structure);
									userDao.updatePolicyById(user);
								}
								Map<String,Object> userMap = new HashMap<String, Object>();
								userMap.put("idList",idsList);
								List<PolicyModel> userList = policyDao.queryUserIdbyDepartmentId(userMap);
					    		map.put("policy", JSONObject.fromObject(policy).toString());
					    		for(PolicyModel po : userList){
					    			if(po.getDeviceManager().getDevice_type() != null){
					    				if(po.getDeviceManager().getDevice_type().equals("android")){
					    					MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(po.getUser().getId()), null, null, 2, map);
					    					taskExecutor.execute(mqProducerThread);
					    				}else if(po.getDeviceManager().getDevice_type().equals("ios")){
					    					deviceToken.add(po.getDeviceManager().getDeviceToken().replaceAll(" ",""));
					    				}
					    			}
					    		}
					    		if(deviceToken.size() > 0){
					    			IosPushUtil.pushDataToIos(deviceToken,null,1,"",map);
					    		}
							}
						}
					}
				}
			}else{
				for(int i=0;i<departmentIdList.size();i++){
					idsList = new ArrayList<Integer>();
//					List<String> list = Arrays.asList(departmentId);
					Integer idStr = queryParentId(departmentIdList,Integer.valueOf(departmentIdList.get(i)));//找到数组中最上级的元素,然后先修改最上级的元素
					String parentId = structureDao.queryParentByparentId(idStr); //判断是不是一级部门
					structure.setId(idStr);
					Integer policyId = null;
					if(parentId != null && !parentId.equals("")){ //如果父id不是空，则不是一级部门
						//当前部门的上级部门的策略
							policyId = structureDao.queryPolicyById(structure);
					}else{
						if(policy.getIsDefault() != 1){  //当前要修改的策略不是默认策略
							policyId = policyDao.queryDefaultPolicy(policy.getOrganization().getId()); //找出默认策略的id
						}else{
							policyId = policy.getId();//如果当前修改的策略是默认策略
						}
					}
					idList = new ArrayList<StructureModel>();
					structureService.queryAllChildrenPolicyId(idStr,strList,idList); //找出当前部门的所有下级部门
					for(StructureModel str : idList){
						if(Integer.valueOf(policy.getId()).equals(str.getPolicy().getId())){
							idsList.add(str.getId());  //遍历所有的下级部门，取出所有下级部门和上级部门策略一样的id
						}
					}
					for(int m=0;m<idsList.size()-1;m++){
						for(int j=idsList.size()-1;j>m;j--){
							if(idsList.get(j).equals(idsList.get(m)))   { 
								idsList.remove(j); 
							} 
						}
					}
					departmentIdList.removeAll(idsList);
					idsList.add(idStr);
					PolicyModel p = policyDao.queryAllInfoById(policyId);
					int count = structureService.updatePolicyByList(policyId,idsList);
					if(count == idsList.size()){
						deviceToken = new ArrayList<String>();
						for(Integer in : idsList){
							//修改完部门的策略后，在修改部门下的人的策略
							structure.setId(in);
							user.setPolicy(p);
							user.setStructure(structure);
							userDao.updatePolicyById(user);
						}
						Map<String,Object> userMap = new HashMap<String, Object>();
						userMap.put("idList",idsList);
						List<PolicyModel> userList = policyDao.queryUserIdbyDepartmentId(userMap);
			    		map.put("policy", JSONObject.fromObject(p).toString());
			    		for(PolicyModel po : userList){
			    			if(po.getDeviceManager().getDevice_type() != null){
			    				if(po.getDeviceManager().getDevice_type().equals("android")){
			    					MqProducerThread mqProducerThread = new MqProducerThread(String.valueOf(po.getUser().getId()), null, null, 2, map);
			    					taskExecutor.execute(mqProducerThread);
			    				}else if(po.getDeviceManager().getDevice_type().equals("ios")){
			    					deviceToken.add(po.getDeviceManager().getDeviceToken().replaceAll(" ",""));
			    				}
			    			}
			    		}
			    		if(deviceToken.size() > 0){
			    			IosPushUtil.pushDataToIos(deviceToken,null,1,"",map);
			    		}
					}
				}
			}
		}
		return result;
	}
	  /**
     * 比较数组中是否有上下级关系的元素
     */
	private Integer queryParentId(List<Integer> list,Integer id){
		//查询部门的
		StructureModel structure = structureDao.queryParentById(id);
		if(structure != null){
			for(Integer s : list){
				if(s.equals(structure.getParent().getId())){
					queryParentId(list,Integer.valueOf(s));
				}else{
					return id;
				}
			}
		}
		return null;
	}
	
	//将两个数组中不一样的值分别放到list中
	private <T> Map<String,List<Integer>> compare(String[] t1, String[] t2) {
		List<String> list1 = Arrays.asList(t1);
		List<String> list2 = Arrays.asList(t2);
		List<Integer> list3 = new ArrayList<Integer>();
		List<Integer> list4 = new ArrayList<Integer>();
		Map<String,List<Integer>> map = new HashMap<String, List<Integer>>();
		if(!list1.containsAll(list2)){
			for(String t : t2){
				if(!list1.contains(t)){
					list4.add(Integer.valueOf(t));  //第二次修改的值的list
					map.put("list4", list4);
				}
			}
			for(String t3 : t1){
				if(!list2.contains(t3)){
					list3.add(Integer.valueOf(t3));   //第一次修改的值
					map.put("list3", list3);
				}
			}
		}else{
			for(String t3 : t1){
					list3.add(Integer.valueOf(t3));   //第一次修改的值
			}
			map.put("list3", list3);
			
		}
		return map;
	}

	@Override
	public List<PolicyModel> findAllByOrgId(Integer orgId) {
		return policyDao.findAllByOrgId(orgId);
	}


	@Override
	public int update(PolicyModel entity) {
		return policyDao.save(entity);
	}

    /**
     * 查询所有策略中的默认策略
     */
	@Override
	public int queryDefaultPolicy(Integer orgId) {
		return policyDao.queryDefaultPolicy(orgId);
	}


	@Override
	public String queryNameById(Integer policyId) {
		
		return policyDao.queryNameById(policyId);
	}


	@Override
	public List<PolicyModel> findAllPolicy(int start,int pageSize,int id,UserModel user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("pageSize",pageSize);
		map.put("orgId", id);
		map.put("user", user);
		return policyDao.findAllPolicy(map);
	}


	@Override
	public int findAllPolicyCount(Integer id,UserModel user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orgId",id);
		map.put("user",user);
		return policyDao.findAllPolicyCount(map);
	}
	
	@Override
	public List<PolicyModel> findLikeName(Integer orgId,String name) {
		Map<String, Object> pMap=new HashMap<String, Object>();
		pMap.put("orgId", orgId);
		pMap.put("search", name);
		return policyDao.findLikeName(pMap);
	}


	@Override
	public int queryCountByName(String name) {
		
		return policyDao.queryCountByName(name);
	}


	@Override
	public PolicyModel queryAllInfoById(Integer id) {
		
		return policyDao.queryAllInfoById(id);
	}


	@Override
	public int queryPolicyCount(Integer orgid) {
		
		return policyDao.queryAllCount(orgid);
	}
	@Override
	public Page queryByParams(Map<String, Object> paramsMap)
	{
		Page p = new Page();
		int total = policyDao.queryCountByParams(paramsMap);
		p.setData(policyDao.queryByParams(paramsMap)); 
		p.setRecordsTotal(total);
		p.setRecordsFiltered(total);
		return p;
	}


	@Override
	public List<PolicyModel> findAllByMap(Map<String, Object> map) {
		return policyDao.findAllByMap(map);
	}


	@Override
	public List<PolicyModel> queryUserIdbyDepartmentId(Map<String, Object> map) {
		
		return policyDao.queryUserIdbyDepartmentId(map);
	}


	@Override
	public PolicyModel queryPolicyByUserId(Integer userId) {
		
		return policyDao.queryPolicyByUserId(userId);
	}
	
}
