package com.so.demosboot.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.so.demosboot.common.baseData.BaseDao;
import com.so.demosboot.modules.share.entity.Items;
import com.so.demosboot.modules.sys.entity.User;
import com.so.demosboot.modules.sys.entity.UserEx;
import com.so.demosboot.modules.sys.entity.UserLink;

@Mapper
public interface UserDao extends BaseDao<User> {

    public List<User> login(User user);

	public int update_lendPoint(User exit_user);

	public User getByUserName(@Param("userId") String userId);

	public void addUserLink(UserLink userLink);

	public List<UserLink> findListLink(UserLink userLink);

	public void sendMsg(UserEx userEx);

	public List<UserEx> findListExMsg(UserEx userEx);

	public void deleteUserEx(UserEx userEx);

	public void deleteUserLink(UserLink userLink);

    List<User> findAllListByTel(User user);
}
