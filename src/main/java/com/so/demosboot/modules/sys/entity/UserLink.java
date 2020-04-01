package com.so.demosboot.modules.sys.entity;

import com.so.demosboot.common.baseData.BaseEntity;

public class UserLink extends BaseEntity<UserLink>{
	
	private String usermine;
	private String userto;
	private String creattime;
	private String bak1;
	public String getUsermine() {
		return usermine;
	}
	public void setUsermine(String usermine) {
		this.usermine = usermine;
	}
	public String getUserto() {
		return userto;
	}
	public void setUserto(String userto) {
		this.userto = userto;
	}
	public String getCreattime() {
		return creattime;
	}
	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	public String getBak1() {
		return bak1;
	}
	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}


}
