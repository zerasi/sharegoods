package com.so.demosboot.modules.share.entity;

import org.hibernate.validator.constraints.Length;

import com.so.demosboot.common.baseData.BaseEntity;

/**
 * 举报信息Entity
 * @author so
 * @version 2020-04-06
 */
public class ShareToReport extends BaseEntity<ShareToReport> {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String orderId;		// 订单编号
	private String repotUser;		// 举报用户
	private String reportContent;		// 举报说明
	private String reportTime;		// 举报时间
	private String reporter;		// 举报者
	
	public ShareToReport() {
		super();
	}

	public ShareToReport(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	

	@Length(min=1, max=40, message="订单编号长度必须介于 1 和 40 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=1, max=40, message="举报用户长度必须介于 1 和 40 之间")
	public String getRepotUser() {
		return repotUser;
	}

	public void setRepotUser(String repotUser) {
		this.repotUser = repotUser;
	}
	
	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	
	@Length(min=1, max=40, message="举报时间长度必须介于 1 和 40 之间")
	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	
	@Length(min=1, max=40, message="举报者长度必须介于 1 和 40 之间")
	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
}