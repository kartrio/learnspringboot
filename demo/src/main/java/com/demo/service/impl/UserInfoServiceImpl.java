package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.UserInfoDao;
import com.demo.model.UserInfo;
import com.demo.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public List<UserInfo> findUserInfo() {
		return userInfoDao.findUserInfo();
	}

	@Override
	public void addUserInfo(UserInfo userInfo) {
		userInfoDao.addUserInfo(userInfo);
	}

	@Override
	public void deleteUserInfo(Integer id) {
		userInfoDao.deleteUserInfo(id);
	}

}
