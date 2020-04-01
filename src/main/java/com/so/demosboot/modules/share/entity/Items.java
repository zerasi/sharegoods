package com.so.demosboot.modules.share.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.so.demosboot.common.baseData.BaseEntity;

/**
 * 图书信息Entity
 * @author so
 * @version 2020-04-06
 */
public class Items extends BaseEntity<Items> {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String itemType;		// 图书类型
	private String name;		// 图书名称
	private String content;		// 图书描述
	private String image;		// 图书图片的存储路径
	private String createsite;		// 发布地点
	private String createtime;		// 发布时间
	private String auditStatue;		// 审核状态 1通过 0不通过
	private String onsell;		// 0下架 1上架
	private String downselftime;		// 下架时间
	private String updatetime;		// 更新时间
	private String belongUser;		// 所属用户
	private String rentType;		// 租用状态
	private Double rentPrice;		// 租金
	
	private String isbn;
	
	private String items_isbn_flag;
	
	public String getItems_isbn_flag() {
		return items_isbn_flag;
	}

	public void setItems_isbn_flag(String items_isbn_flag) {
		this.items_isbn_flag = items_isbn_flag;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	private String typeName;
	
	public Items() {
		super();
	}

	public Items(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	

	@Length(min=1, max=40, message="图书类型长度必须介于 1 和 40 之间")
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	@Length(min=1, max=20, message="图书名称长度必须介于 1 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=100, message="图书描述长度必须介于 1 和 100 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=1, max=100, message="图书图片的存储路径长度必须介于 1 和 100 之间")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Length(min=1, max=20, message="发布地点长度必须介于 1 和 20 之间")
	public String getCreatesite() {
		return createsite;
	}

	public void setCreatesite(String createsite) {
		this.createsite = createsite;
	}
	
	@Length(min=1, max=40, message="发布时间长度必须介于 1 和 40 之间")
	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	@Length(min=1, max=255, message="审核状态 1通过 0不通过长度必须介于 1 和 255 之间")
	public String getAuditStatue() {
		return auditStatue;
	}

	public void setAuditStatue(String auditStatue) {
		this.auditStatue = auditStatue;
	}
	
	@Length(min=1, max=10, message="0下架 1上架长度必须介于 1 和 10 之间")
	public String getOnsell() {
		return onsell;
	}

	public void setOnsell(String onsell) {
		this.onsell = onsell;
	}
	
	@Length(min=0, max=40, message="下架时间长度必须介于 0 和 40 之间")
	public String getDownselftime() {
		return downselftime;
	}

	public void setDownselftime(String downselftime) {
		this.downselftime = downselftime;
	}
	
	@Length(min=0, max=40, message="更新时间长度必须介于 0 和 40 之间")
	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
	@Length(min=1, max=40, message="所属用户长度必须介于 1 和 40 之间")
	public String getBelongUser() {
		return belongUser;
	}

	public void setBelongUser(String belongUser) {
		this.belongUser = belongUser;
	}
	
	@Length(min=1, max=40, message="租用状态长度必须介于 1 和 40 之间")
	public String getRentType() {
		return rentType;
	}

	public void setRentType(String rentType) {
		this.rentType = rentType;
	}
	
	@NotNull(message="租金不能为空")
	public Double getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Double rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}