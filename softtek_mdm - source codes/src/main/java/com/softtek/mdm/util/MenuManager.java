package com.softtek.mdm.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.softtek.mdm.model.MenuModel;
import com.softtek.mdm.model.MenuNodeModel;

import jodd.util.StringUtil;

@Component
public class MenuManager {

	@Resource(name="messageSourceService")
	private MessageSource messageSource;
	
	/**
	 * 
	 * @param all 所有的菜单
	 * @param list 当前用户所具有的菜单
	 * @return
	 */
	public MenuNodeModel buildPartTree(List<MenuModel> all,List<MenuModel> list){
		if(list.size()>0){
			for (int i=0;i<list.size();i++) {
				buildParent(all,list.get(i),list);
			}
		}
		return buildTree(list);
	}
	
	/**
	 * 将菜单名称国际化
	 * @param list
	 * @return
	 */
	public List<MenuModel> sortAndTransformToInternational(List<MenuModel> list){
		if(list!=null&&list.size()>0){
			//按照weight冲销到大排序
			for(int i=0;i<list.size();i++){
				for(int j=0;j<list.size()-i-1;j++){
					if(list.get(j).getWeight()>list.get(j+1).getWeight()){
						MenuModel menu=list.get(j);
						list.set(j, list.get(j+1));
						list.set(j+1, menu);
					}
				}
			}
			//数据库local的名字只保存国际化的key
			for(int index=0;index<list.size();index++){
				String localName=messageSource.getMessage(list.get(index).getName(), null,LocaleContextHolder.getLocale());
				list.get(index).setName(localName);
			}
			return list;
		}
		return null;
		
	}
	
	/**
	 * 建立一棵树
	 * @param list
	 * @return
	 */
	public MenuNodeModel buildTree(List<MenuModel> list){
		list=sortAndTransformToInternational(list);
		if(list!=null&&list.size()>0){
			for (MenuModel ste : list) {
				if (ste.getParent() == null) {
					MenuNodeModel node=new MenuNodeModel();
					node.setCurrent(ste);
					return build(list,node);
				}
			}
		}
		return null;
	}
	
	/**
	 * 递归遍历查询子节点
	 * @param list
	 * @param node
	 * @return
	 */
	private MenuNodeModel build(List<MenuModel> list,MenuNodeModel node){
		List<MenuNodeModel> children=getChildren(list, node.getCurrent());
		if(!children.isEmpty()){
			node.setChildren(children);
			for (MenuNodeModel c : children) {
				build(list,c);
			}
		}
		return node;
	}
	
	/**
	 * 递归遍历查询父节点
	 * @param list
	 * @param node
	 * @return
	 */
	private List<MenuModel> buildParent(List<MenuModel> list,MenuModel node,List<MenuModel> currentList){
		MenuModel parent=getParent(list, node);
		if(parent==null){
			return currentList;
		}
		if(!currentList.contains(parent)){
			currentList.add(parent);
		}
		buildParent(list,parent,currentList);
		return currentList;
	}
	
	
	/**
	 * 根据MenuModel，找到孩子节点
	 * @param list
	 * @param menu
	 * @return
	 */
	private List<MenuNodeModel> getChildren(List<MenuModel> list,MenuModel menu){
		List<MenuNodeModel> nodes=new ArrayList<MenuNodeModel>();
		for (MenuModel m : list) {
			if(m.getParent()!=null){
				if (StringUtil.equals(menu.getId().toString(), m.getParent().getId().toString())) {
					MenuNodeModel node=new MenuNodeModel();
					node.setCurrent(m);
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
	
	/**
	 * 根据MenuModel，找到父亲
	 * @param list
	 * @param menu
	 * @return
	 */
	private MenuModel getParent(List<MenuModel> list,MenuModel menu){
		if(menu.getParent()!=null){
			for (MenuModel m : list) {
				if (StringUtil.equals(menu.getParent().getId().toString(), m.getId().toString())) {
					return m;
				}
			}
		}
		return null;
	}
	
}
