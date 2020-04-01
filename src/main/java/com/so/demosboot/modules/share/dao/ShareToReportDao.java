package com.so.demosboot.modules.share.dao;

import org.apache.ibatis.annotations.Mapper;

import com.so.demosboot.common.baseData.BaseDao;
import com.so.demosboot.modules.share.entity.ShareToReport;

/**
 * 举报信息DAO接口
 * @author so
 * @version V1.0
 */
@Mapper
public interface ShareToReportDao extends BaseDao<ShareToReport> {
	
}