package com.so.demosboot.modules.share.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.so.demosboot.common.baseData.BaseEntity;

/**
 * 图书种类Entity
 * @author so
 * @version 2020-04-06
 */
public class Category extends BaseEntity<Category> {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;		// 名称
	private Integer sort;		// 排序
	
	private List<Items> items = new ArrayList<Items>();
	
	public Category() {
		super();
	}

	public Category(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	

	@Length(min=1, max=50, message="名称长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}
	
}