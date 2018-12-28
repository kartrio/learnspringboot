package com.demo.service;

import java.util.List;

import com.demo.model.UserInfo;

public interface UserInfoService {
	public List<UserInfo> findAll();

	public UserInfo findUserByName(String username);
	public UserInfo findById(int id);
}
