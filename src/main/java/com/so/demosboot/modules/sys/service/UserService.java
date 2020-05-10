package com.so.demosboot.modules.sys.service;


import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import com.so.demosboot.common.baseData.BaseService;
import com.so.demosboot.modules.share.entity.Items;
import com.so.demosboot.modules.sys.dao.UserDao;
import com.so.demosboot.modules.sys.entity.User;
import com.so.demosboot.modules.sys.entity.UserEx;
import com.so.demosboot.modules.sys.entity.UserLink;

@Service
public class UserService extends BaseService<UserDao, User> {
	
	public User login (User user){
	    List<User> listUser = dao.login(user);
	    if(listUser.size() > 0){
	    	return listUser.get(0);
	    }
	    return null;
    }

	public User update_lendPoint(User user) {
		User exit_user = dao.getById(user.getId());
		exit_user.setLendPoint(exit_user.getLendPoint().add(user.getLendPoint()));;
		int rows = dao.update_lendPoint(exit_user);
		return exit_user;
		
	}

	public User getByUserName( String userId) {
		
		return dao.getByUserName(userId);
	}

	public void update_len(User user) {
		dao.update_lendPoint(user);
		
	}

	public void addUserLink(UserLink userLink) {
		this.dao.addUserLink(userLink);
	}

	public List<UserLink> findListLink(UserLink userLink) {
		return dao.findListLink(userLink);
	}

	public void sendMsg(UserEx userEx) {
		this.dao.sendMsg(userEx);
	}

	public List<UserEx> findListExMsg(UserEx userEx) {
		return dao.findListExMsg(userEx);
	}

	public void deleteUserLink(UserLink userLink) {
		dao.deleteUserLink(userLink);
		
	}

	public void deleteUserEx(UserEx userEx) {
		dao.deleteUserEx(userEx);
		
	}

    public List<User> findAllListByTel(User user) {
		return dao.findAllListByTel(user);
    }
}
