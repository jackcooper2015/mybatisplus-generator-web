package com.reapal.model;

import java.io.Serializable;

public class Code implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 功能ID */
	private Integer funcId;
	/** 功能编号 */
	private String funcCode;
	/** 功能名称 */
	private String funcName;
	/** 操作链接 */
	private String url;
	/** 权限类型 */
	private String authType;
	/** 是否菜单项 */
	private String isMenu;
	/** 父ID */
	private Integer parentId;
	/** 显示层级编号 */
	private String viewLevelNo;
	/** 创建人 */
	private String createUserId;
	/** 创建时间 */
	private java.util.Date createDateTime;
	/** 最后修改人 */
	private String modifyUserId;
	/** 最后修改时间 */
	private java.util.Date modifyDateTime;
	/** 删除标记（1有效、2无效） */
	private String deleteFlag;

	public Integer getFuncId() {
		return this.funcId;
	}

	public void setFuncId(Integer value) {
		this.funcId = value;
	}

	public String getFuncCode() {
		return this.funcCode;
	}

	public void setFuncCode(String value) {
		this.funcCode = value;
	}

	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncName(String value) {
		this.funcName = value;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String value) {
		this.url = value;
	}

	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String value) {
		this.authType = value;
	}

	public String getIsMenu() {
		return this.isMenu;
	}

	public void setIsMenu(String value) {
		this.isMenu = value;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer value) {
		this.parentId = value;
	}

	public String getViewLevelNo() {
		return this.viewLevelNo;
	}

	public void setViewLevelNo(String value) {
		this.viewLevelNo = value;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String value) {
		this.createUserId = value;
	}

	public java.util.Date getCreateDateTime() {
		return this.createDateTime;
	}

	public void setCreateDateTime(java.util.Date value) {
		this.createDateTime = value;
	}

	public String getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(String value) {
		this.modifyUserId = value;
	}

	public java.util.Date getModifyDateTime() {
		return this.modifyDateTime;
	}

	public void setModifyDateTime(java.util.Date value) {
		this.modifyDateTime = value;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String value) {
		this.deleteFlag = value;
	}
	

}

