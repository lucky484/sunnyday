package com.softtek.mdm.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softtek.mdm.model.ManagerModel;
import com.softtek.mdm.model.NodeModel;
import com.softtek.mdm.model.OrganizationModel;
import com.softtek.mdm.model.State;
import com.softtek.mdm.model.StructureModel;
import com.softtek.mdm.model.UserDepartmentModel;
import com.softtek.mdm.model.UserModel;
import com.softtek.mdm.model.UserRoleDepartmentModel;
import com.softtek.mdm.service.DeviceBasicInfoService;
import com.softtek.mdm.service.PolicyService;
import com.softtek.mdm.service.StructureService;
import com.softtek.mdm.service.UserDepartmentService;
import com.softtek.mdm.service.UserRoleDepartmentService;
import com.softtek.mdm.service.UserService;
import com.softtek.mdm.status.SessionStatus;

/**
 * 处理树形节点
 * @author color.wu
 *
 */
@Component
public class TreeManager {

	@Autowired
	private UserService userService;
	@Autowired
	private DeviceBasicInfoService deviceBasicInfoService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private UserRoleDepartmentService userRoleDepartmentService;
	@Autowired
	private UserDepartmentService userDepartmentService;
	@Autowired
	private StructureService structureService;
	
	/**
	 * 获取当前用户所管理的部门的list集合，形成一个完整的父类集合
	 * @param list 当前机构下的所有部门
	 * @param currentList 该用户所能管理的部门集合
	 * @return
	 */
	public List<StructureModel> generateList(List<StructureModel> list,List<StructureModel> currentList){
		boolean isExists=isExistsRoot(currentList);
		if(!isExists){
			currentList=addRootToList(list,currentList);
		}
		for (int i=0;i<currentList.size();i++) {
			if(currentList.get(i).getParent()!=null){
				//非根节点
				StructureModel currParent=getParent(currentList,currentList.get(i));
				if(currParent!=null){
					//当前节点中存在该节点的父类
					currentList.get(i).setParent(currParent);
				}else{
					//当前节点中不存在该节点的父类
					StructureModel temp=currentList.get(i);
					temp=getParent(list,temp);
					while((temp=getParent(currentList,temp))==null){
						temp=getParent(list,temp);
						if(temp==null){
							break;
						}
					}
					currentList.get(i).setParent(temp);
				}
			}
		}
		return currentList;
	}
	
	/**
	 * 找到当前孩子的父亲
	 * @param list
	 * @param structure
	 * @return
	 */
	private StructureModel getParent(List<StructureModel> list,StructureModel structure){
		if(structure==null){
			return null;
		}
		for (StructureModel ste : list) {
			if(ste.getId().equals(structure.getParent().getId())){
				return ste;
			}
		}
		return null;
	}
	
	/**
	 * 判断当前列表中是否存在根节点
	 * @param list
	 * @return
	 */
	private boolean isExistsRoot(List<StructureModel> list){
		for (StructureModel ste : list) {
			if(ste.getParent()==null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 往目标列表中添加根节点
	 * @param list
	 * @param targetList
	 * @return
	 */
	private List<StructureModel> addRootToList(List<StructureModel> list,List<StructureModel> targetList){
		for (StructureModel ste : list) {
			if(ste.getParent()==null&&!targetList.contains(ste)){
				targetList.add(ste);
				break;
			}
		}
		return targetList;
	}
	
	/**
	 * 建立一棵带设备数目的节点
	 * @param list
	 * @return
	 */
	public NodeModel bulidTreeIncludeDevice(List<StructureModel> list,Integer orgId){
		NodeModel node=buildTree(list);
		bulidIncludeDevice(node,orgId);
		return node;
	}
	
	/**
	 * 建立一棵带用户数目的节点
	 * @param list
	 * @return
	 */
	public NodeModel bulidTreeIncludeUser(List<StructureModel> list,Integer orgId){
		NodeModel node=buildTree(list);
		bulidIncludeUser(node,orgId);
		return node;
	}
	
	
	/**
	 * 建立一棵带用户数目的节点
	 * @param list
	 * @return
	 */
	public NodeModel bulidTreeContainUser(List<StructureModel> list,Integer orgId,UserModel user){
		NodeModel node=buildTree(list);
		bulidContainUser(node,orgId,user);
		return node;
	}
	
	
	/**
	 * 建立一棵树
	 * @param list
	 * @return
	 */
	public NodeModel buildTree(List<StructureModel> list){
		for (StructureModel ste : list) {
			if (ste.getParent() == null) {
				NodeModel node=new NodeModel();
				node.setHref("javascript:void(0)");
				node.setTags(ste);
				node.setText(ste.getName());
				return build(list,node);
			}
		}
		return null;
	}
	
	//将策略设置到树上
	public List<NodeModel> buildTreeIncludePolicy(List<StructureModel> list,Integer policyId){
		List<NodeModel> nodeList = new ArrayList<NodeModel>();
		Integer fatherId = 0;
		// 查找机构下面的所有顶级部门
		for (StructureModel ste : list) {
			if (ste.getParent() == null) {
				fatherId = ste.getId();
			}
		}
		// 遍历机构下面的顶级部门以及下面的子部门
		for (StructureModel ste : list) {
			if (ste.getParent() != null) {
				if(fatherId.toString().equals(ste.getParent().getId().toString())){
					String name = "";
					if(ste.getPolicy().getId() != null){
						name = policyService.queryNameById(ste.getPolicy().getId());
					}
					NodeModel node=new NodeModel();
					node.setHref("javascript:void(0)");
					node.setTags(ste);
				  if(policyId.equals(ste.getPolicy().getId())){
					  node.setText(ste.getName());
				  }else{
					  if(name != null && !name.equals("")){
						  node.setText(ste.getName()+"("+name+")");
					  }else{
						  node.setText(ste.getName());
					  }
				  }
					nodeList.add(build1(list,node));
				}
			}
		}
		return nodeList;
	}
	
	/**
	 * 建立多个顶级父节点
	 * @param list
	 * @return
	 */
	public List<NodeModel> buildMoreTree(List<StructureModel> list){
		List<NodeModel> nodeList = new ArrayList<NodeModel>();
		Integer fatherId = 0;
		// 查找机构下面的所有顶级部门
		for (StructureModel ste : list) {
			if (ste.getParent() == null) {
				fatherId = ste.getId();
			}
		}
		// 遍历机构下面的顶级部门以及下面的子部门
		for (StructureModel ste : list) {
			State state = null;
			if (ste.getParent() != null) {
				if(fatherId.toString().equals(ste.getParent().getId().toString())){
					NodeModel node=new NodeModel();
					node.setHref("javascript:void(0)");
					node.setTags(ste);
					node.setText(ste.getName());
					if(Constant.YES.equals(ste.getIsAuth())) {
						state = new State();
						state.setChecked(false);
						state.setDisabled(true);
						state.setExpanded(false);
						state.setSelected(false);
						node.setState(state);
					}
					nodeList.add(build(list,node));
				}
			}
		}
		return nodeList;
	}
	   
	public int bulidIncludeDevice(NodeModel node,Integer orgId){
		int sum=0;
		List<Integer> list = null;
		if(node.getNodes()==null){
			//这里查询该（最末）孩子节点下面的用户数目
			list = new ArrayList<Integer>();
			list.add(node.getTags().getId());
			int count=deviceBasicInfoService.findCountBySteId(list, orgId);
			node.setText(node.getText()+"(共"+count+"台)");
			return count;
		}else{
			list = new ArrayList<Integer>();
			for (NodeModel n : node.getNodes()) {
				sum+=bulidIncludeDevice(n,orgId);
			}
			list.add(node.getTags().getId());
			int current=deviceBasicInfoService.findCountBySteId(list, orgId);
			sum+=current;
			node.setText(node.getText()+"(共"+sum+"台)");
			return sum;
		}
	}
	
	
	public int bulidContainUser(NodeModel node,Integer orgId,UserModel user){
		int sum=0;
		if(node.getNodes()==null){
			//这里查询该（最末）孩子节点下面的用户数目
			int count=userService.findCountByMaps(node.getTags().getId(), orgId,user);
			node.setText(node.getText()+"(共"+count+"人)");
			return count;
		}else{
			for (NodeModel n : node.getNodes()) {
				sum+=bulidIncludeUser(n,orgId);
			}
			int current=userService.findCountByMaps(node.getTags().getId(), orgId,user);
			sum+=current;
			node.setText(node.getText()+"(共"+sum+"人)");
			return sum;
		}
	}
	
	public int bulidIncludeUser(NodeModel node,Integer orgId){
		int sum=0;
		if(node.getNodes()==null){
			//这里查询该（最末）孩子节点下面的用户数目
			int count=userService.findCountByMap(node.getTags().getId(), orgId);
			node.setText(node.getText()+"(共"+count+"人)");
			return count;
		}else{
			for (NodeModel n : node.getNodes()) {
				sum+=bulidIncludeUser(n,orgId);
			}
			int current=userService.findCountByMap(node.getTags().getId(), orgId);
			sum+=current;
			node.setText(node.getText()+"(共"+sum+"人)");
			return sum;
		}
	}
	
	/**
	 * 递归遍历查询子节点
	 * @param list
	 * @param node
	 * @return
	 */
	private NodeModel build(List<StructureModel> list,NodeModel node){
		List<NodeModel> children = getChildren(list, node.getTags());
		if(!children.isEmpty()){
			for (NodeModel c : children) {
				build(list,c);
			}
			node.setNodes(children);
		}
		return node;
		
	}
	
	private NodeModel build1(List<StructureModel> list,NodeModel node){
		List<NodeModel> children=getChildren1(list, node.getTags());
		if(!children.isEmpty()){
			for (NodeModel c : children) {
				build1(list,c);
			}
			node.setNodes(children);
		}
		return node;
		
	}
	/**
	 * 根据structure，找到孩子节点
	 * @param list
	 * @param structure
	 * @return
	 */
	private List<NodeModel> getChildren(List<StructureModel> list,StructureModel structure){
		List<NodeModel> nodes=new ArrayList<NodeModel>();
		State state = null;
		for (StructureModel ste : list) {
			if(ste.getParent()!=null){
				if (StringUtil.equals(structure.getId().toString(), ste.getParent().getId().toString())) {
					NodeModel node=new NodeModel();
					node.setHref("javascript:void(0)");
					node.setTags(ste);
					node.setText(ste.getName());
					if(Constant.YES.equals(ste.getIsAuth())) {
						state = new State();
						state.setChecked(false);
						state.setDisabled(true);
						state.setExpanded(false);
						state.setSelected(false);
						node.setState(state);
					}
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
	
	/**
	 * 根据structure，找到孩子节点
	 * @param list
	 * @param structure
	 * @return
	 */
	private List<NodeModel> getChildren1(List<StructureModel> list,StructureModel structure){
		List<NodeModel> nodes=new ArrayList<NodeModel>();
		for (StructureModel ste : list) {
			if(ste.getParent()!=null){
				if (StringUtil.equals(structure.getId().toString(), ste.getParent().getId().toString())) {
					String name = "";
					if(ste.getPolicy().getId() != null){
						name = policyService.queryNameById(ste.getPolicy().getId());
					}
					NodeModel node=new NodeModel();
					node.setHref("javascript:void(0)");
					node.setTags(ste);
				    if(Integer.valueOf(structure.getPolicy().getId()).equals(Integer.valueOf(ste.getPolicy().getId()))){
				    	node.setText(ste.getName());
				    }else{
				    	if(name != null && !name.equals("")){
				    		node.setText(ste.getName()+"("+name+")");
				    	}else{
				    		node.setText(ste.getName());
				    	}
				    }
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
	
	/**
	 * 初始化关于树的session
	 * @param session
	 */
	public void InitTreeSession(HttpSession session){
		ManagerModel manager=(ManagerModel) session.getAttribute(SessionStatus.SOFTTEK_MANAGER.toString());
		OrganizationModel organizaiton=(OrganizationModel) session.getAttribute(SessionStatus.SOFTTEK_ORGANIZATION.toString());
		if(manager!=null&&organizaiton!=null){
			List<StructureModel> list=(List<StructureModel>) structureService.findAllByOrgID(organizaiton.getId());
			//判断管理员是否被锁住或者未激活
			if(manager.getUser()==null){
				session.setAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString(), list);
			}else{
				UserRoleDepartmentModel uRD=userRoleDepartmentService.findOneByMaps(organizaiton.getId(), manager.getUser().getId());
				if(uRD==null){
					return;
				}
				List<UserDepartmentModel> uRDlist=userDepartmentService.findByFId(uRD.getId());
				List<Integer> ids=new ArrayList<Integer>();
				for (UserDepartmentModel urd : uRDlist) {
					ids.add(urd.getStructure().getId());
				}
				List<StructureModel> currentList=structureService.findByIds(ids);
				List<StructureModel> sessionDepartmentList=generateList(list, currentList);
				session.setAttribute(SessionStatus.SOFTTEK_DEPARTMENT.toString(), sessionDepartmentList);
			}
		}
	}
	
	public List<StructureModel> onlyGenerateList(List<StructureModel> list,List<StructureModel> currentList){
		List<StructureModel> sessionDepartmentList=generateList(list, currentList);
		return sessionDepartmentList;
	}
}
