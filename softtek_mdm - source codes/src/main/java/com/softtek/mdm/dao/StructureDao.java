package com.softtek.mdm.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.softtek.mdm.model.StructureModel;

/**
 * 
 * @author color.wu
 */
public interface StructureDao extends CrudMapper<StructureModel, Integer> {

	Collection<StructureModel> findAllByOrgID(Integer id);
	
	List<StructureModel> findAllByMap(Map<String, Object> map);

	/**
	 * ���ݲ���id�����Ӳ���id��������
	 * 
	 * @param structureId
	 * @return
	 */
	Integer[] findSubsBysId(Integer structureId);

	public StructureModel queryParentById(int id);

	public void updateParentIdById(int id, int parentId);

	public int deleteStructureById(List<Integer> list);

	public int updatePolicyByList(Map<String,Object> map);

	public Integer queryPolicyById(StructureModel structure);

	public StructureModel queryParentNameById(Integer parentId);

	public int queryCountByName(Map<String, Object> map);

	public int queryUserById(List<Integer> list);

	public int queryParentPolicyById(Integer id);

	Collection<StructureModel> findByIds(List<Integer> ids);

	public List<StructureModel> quertAllDepartmentByPolicyId(Integer policyId);
	
	public List<StructureModel> queryAllchildren(Integer id);
	
	public int updatePolicyById(StructureModel structure);
	
	public int queryPolicyIdById(Integer id);
	
	public String queryNameById(Integer id);
	
	public Integer queryOrgId(Integer id);
	
    public List<Integer> queryDepartmentByDefaultPolicy(Map<String,Object> map);
    /**
     * 查询父部门的父id
     * @param id
     * @return
     */
    public String queryParentByparentId(Integer id);
    
    public int queryTopDepartmentId(Integer orgId);
    
    public List<String> queryChildName(Integer id);
    
	public void updateUserDepartment(Map<String,Object> map);

	StructureModel findOneByOrgId(Integer id);

	List<StructureModel> getStructureListById(Integer id);
	/**
	 * 获取parentId的子女节点
	 * @param parentId
	 * @return
	 */
	List<StructureModel> getChildren(Integer parentId);
	/**
	 * 获取某个机构下的顶级节点
	 * @return
	 */
	StructureModel getRoot(Integer orgId);
	
}
