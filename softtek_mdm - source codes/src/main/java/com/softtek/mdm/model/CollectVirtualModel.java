package com.softtek.mdm.model;

/**
 * 虚拟集合组关联表
 * @author jane.hui
 *
 */
public class CollectVirtualModel {
	/**
	 * 编号
	 */
	private Integer id;
	
	/**
	 * 虚拟组名称
	 */
	private String name;
	
	/**
	 * 虚拟集合id
	 */
	private Integer collectionId;
	
	/**
	 * 虚拟集合名称
	 */
	private String collectionName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
}
