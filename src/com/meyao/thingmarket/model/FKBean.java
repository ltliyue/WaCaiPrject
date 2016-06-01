package com.meyao.thingmarket.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class FKBean extends BmobObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fkInfo;
	private String relationType;
	private String appName;
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFkInfo() {
		return fkInfo;
	}

	public void setFkInfo(String fkInfo) {
		this.fkInfo = fkInfo;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
