package com.so.demosboot.modules.cms.dao;

import org.apache.ibatis.annotations.Mapper;

import com.so.demosboot.common.baseData.BaseDao;
import com.so.demosboot.modules.cms.entity.Comments;

/**
 * 评论信息DAO接口
 * @author so
 * @version V1.0
 */
@Mapper
public interface CommentsDao extends BaseDao<Comments> {
	
}