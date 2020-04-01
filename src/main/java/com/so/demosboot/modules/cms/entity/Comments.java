package com.so.demosboot.modules.cms.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.so.demosboot.common.baseData.BaseEntity;

/**
 * 评论信息Entity
 * @author so
 * @version 2020-04-06
 */
public class Comments extends BaseEntity<Comments> {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String tableName;		// 表名
	private String tableId;		// 表编号
	private String commentContent;		// 评论内容
	private String commentTime;		// 评论时间
	private String commentUser;		// 评论用户
	private String parent;		// 父级编号
	
	public Comments() {
		super();
	}

	public Comments(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	

	@Length(min=1, max=40, message="表名长度必须介于 1 和 40 之间")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Length(min=1, max=40, message="表编号长度必须介于 1 和 40 之间")
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
	@Length(min=0, max=40, message="评论时间长度必须介于 0 和 40 之间")
	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	
	@Length(min=0, max=40, message="评论用户长度必须介于 0 和 40 之间")
	public String getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}
	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
}