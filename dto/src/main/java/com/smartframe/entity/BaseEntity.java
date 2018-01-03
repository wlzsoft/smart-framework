package com.smartframe.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 5862520462271034509L;
	
	public Integer createId;
	
	public Date createTime;
	
	public Integer updateId;
	
	public Date updateTime;
	
	public String remark;
	
	public Boolean deleteflag;

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(Boolean deleteflag) {
		this.deleteflag = deleteflag;
	}
	
}
