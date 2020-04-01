package com.so.demosboot.modules.share.dao;

import org.apache.ibatis.annotations.Mapper;

import com.so.demosboot.common.baseData.BaseDao;
import com.so.demosboot.modules.share.entity.ShareOrderInfo;

/**
 * 订单信息DAO接口
 * @author so
 * @version V1.0
 */
@Mapper
public interface ShareOrderInfoDao extends BaseDao<ShareOrderInfo> {
	
}