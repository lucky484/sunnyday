package com.softtek.mdm.model;

/**
 * node节点状态
 * @author jane.hui
 *
 */
public class State {

	/**
	 * 是否复选框选中(true：选中  false：不选中)
	 */
	private boolean checked;
	
	/**
	 * 是否禁用复选框(true：禁用  false：不禁用)
	 */
	private boolean disabled;
	
	/**
	 * 是否展开节点(true:展开  false：不展开)
	 */
	private boolean expanded;
	
	/**
	 * 是否选择该节点(true:选中 false：不选中)
	 */
	private boolean selected;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
