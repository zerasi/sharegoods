package com.so.demosboot.modules.share.dao;

import org.apache.ibatis.annotations.Mapper;

import com.so.demosboot.common.baseData.BaseDao;
import com.so.demosboot.modules.share.entity.Category;

/**
 * 图书种类DAO接口
 * @author so
 * @version V1.0
 */
@Mapper
public interface CategoryDao extends BaseDao<Category> {
	
}