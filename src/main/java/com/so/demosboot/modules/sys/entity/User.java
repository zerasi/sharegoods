package com.so.demosboot.modules.sys.entity;

import java.math.BigDecimal;

import com.so.demosboot.common.baseData.BaseEntity;

public class User extends BaseEntity<User> {

	private String username;		// 用户名
	private String password;		// 密码
	private String role;		// 角色
	private String isBlock;		// 是否锁定
	
	private String tel;
	private String email;
	
	private BigDecimal lendPoint;

	private String msgCode;


	public BigDecimal getLendPoint() {
		return lendPoint;
	}

	public void setLendPoint(BigDecimal lendPoint) {
		this.lendPoint = lendPoint;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getIsBlock() {
		return isBlock;
	}

	public void setIsBlock(String isBlock) {
		this.isBlock = isBlock;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
}
