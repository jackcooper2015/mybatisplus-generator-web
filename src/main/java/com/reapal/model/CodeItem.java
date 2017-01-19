package com.reapal.model;

import java.io.Serializable;

public class CodeItem implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
	/*
	 * 校验标签   Verification
	 * #VNN 非空	Not Null
	 * #VI  整形数字	Int
	 * #VD  实数	
	 * #VE  Email
	 * #VM  手机号		Mobeil
	 * #VT  固话号		Telephone
	 * 
	 * 编辑标签	Edit
	 * #EPS1100& 参数下拉框		Parameter Select
	 * #EPC1100& 参数复选框		Parameter Checkbox 
	 * #EPR1100& 参数单选框		Parameter Radio
	 * #EDS0& 	部门下拉框		Department Select
	 * #EAS0& 	部门下拉框		Area Select
	 * #EOR		开关单选框		Open Radio   0否，1是
	 * #ERT		富文本编辑框		Rich Text editor
	 * 
	 * 逻辑标签	Logic
	 * #LFK		外键--从表
	 * #LCP		代码转换名称字段--参数 		Change Parameter
	 * #LCD		代码转换名称字段--部门		Change Department
	 * #LCA		代码转换名称字段--区域    	Change Area
	 * 
	 * 其他标签	Other	
	 * #OS		列表显示字段	Show
	 * 
	 * */
	private String itemName;
	private String itemType;
	private String remarks;

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
}

