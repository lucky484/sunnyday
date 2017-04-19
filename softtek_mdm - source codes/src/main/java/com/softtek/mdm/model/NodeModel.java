package com.softtek.mdm.model;

import java.util.List;

public class NodeModel {

	private String text;
	
	private String href;
	
	private State state;
	
	private StructureModel tags;
	
	private List<NodeModel> nodes;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public StructureModel getTags() {
		return tags;
	}

	public void setTags(StructureModel tags) {
		this.tags = tags;
	}

	public List<NodeModel> getNodes() {
		return nodes;
	}

	public void setNodes(List<NodeModel> nodes) {
		this.nodes = nodes;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}