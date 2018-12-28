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
	public List<UserInfo> findAll() {
		return userInfoDao.findAll();
	}

	@Override
	public UserInfo findUserByName(String username) {
		return userInfoDao.findUserByName(username);
	}

	@Override
	public UserInfo findById(int id) {
		return userInfoDao.findById(id);
	}

}
