package com.so.demosboot.modules.share.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.so.demosboot.common.baseData.BaseEntity;

/**
 * 订单信息Entity
 * @author so
 * @version 2020-04-06
 */
public class ShareOrderInfo extends BaseEntity<ShareOrderInfo> {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String itemId;		// 图书编号
	private String userId;		// 用户编号
	private String orderTime;		// 订单时间
	private Integer rentTimes;		// 租用时间
	private Date shouldBackTime;		// 截止归还时间
	private String isBack;		// 是否归还
	private String backTime;		// 归还时间
	private String isComment;//是否评价
	private String isReport;//是否举报
	
	private String itemName;
	private String userName;
	
	public ShareOrderInfo() {
		super();
	}

	public ShareOrderInfo(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	

	@Length(min=1, max=40, message="图书编号长度必须介于 1 和 40 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=1, max=40, message="用户编号长度必须介于 1 和 40 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=1, max=40, message="订单时间长度必须介于 1 和 40 之间")
	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	@NotNull(message="租用时间不能为空")
	public Integer getRentTimes() {
		return rentTimes;
	}

	public void setRentTimes(Integer rentTimes) {
		this.rentTimes = rentTimes;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="截止归还时间不能为空")
	public Date getShouldBackTime() {
		return shouldBackTime;
	}

	public void setShouldBackTime(Date shouldBackTime) {
		this.shouldBackTime = shouldBackTime;
	}
	
	@Length(min=1, max=20, message="是否归还长度必须介于 1 和 20 之间")
	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	
	@Length(min=0, max=40, message="归还时间长度必须介于 0 和 40 之间")
	public String getBackTime() {
		return backTime;
	}

	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}

	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	public String getItemName() {
		return itemName;
	}

	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}