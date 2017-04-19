package com.softtek.mdm.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserDepartmentModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;

public interface StructureService {

	Collection<StructureModel> findAllByOrgID(Integer id);
	
	List<StructureModel> findAllByMap(Map<String, Object> map);
	
	int save(StructureModel entity,UserDepartmentModel userDepartmentModel);
	
	StructureModel getParents(Integer id);

	int update(StructureModel structure);
	
	public StructureModel queryParentById(int id);
	
	public void updateParentIdById(int id,int parentId);
	
	 public int deleteStructureById(UserRoleDepartmentModel uRD,List<Integer> list);
	 
	 StructureModel findOne(Integer id);
    
	 public int updatePolicyByList(Integer policyId,List<Integer> list);
	 
	 public Integer queryPolicyById(StructureModel structure);
	 
	 public void queryParentNameById(Integer parentId);

	 public String buildDepartmentName(Integer valueOf);
	 
	 public int queryCountByName(Map<String,Object> map);
	 
	 public int queryUserById(List<Integer> list);
	 
	 public int queryParentPolicyById(Integer id);
	 
	 List<StructureModel> findByIds(List<Integer> ids);
	 
	 public List<StructureModel> queryAllchildren(Integer id);

	 void queryAllChildrenId(Integer id, List<StructureModel> list,List<Integer> idList);
	
	 public void queryAllChildrenPolicyId(Integer id,List<StructureModel> list,List<StructureModel> idList);
	 
	 public int queryPolicyIdById(Integer id);
	 
	 public String queryNameById(Integer id);
	 
	 public Integer queryOrgId(Integer id);

   	 String queryParentByparentId(Integer departmentId);
   	 
   	 public List<String> queryChildName(Integer id);
   /**
    * 获取parentId的子女节点
    * 如果parentId=null，那么就返回机构编号为orgId的根节点和它的子女节点
    * @param parentId
    * @param orgId
    * @return list
    */
   	List<StructureModel> getChildren(Integer parentId,Integer orgId);
   	
   	
}
