package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.UserInfoMapper;
import com.demo.model.UserInfo;
import com.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Override
	public List<UserInfo> findAll() {
		return userInfoMapper.findAll();
	}

	@Override
	public void addUser(UserInfo userInfo) {
		userInfoMapper.addUser(userInfo);;		
	}

	@Override
	public void deleteUser(Integer id) {
		userInfoMapper.deleteUser(id);;	
	}

}
