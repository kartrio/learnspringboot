package com.demo.service;

import java.util.List;

import com.demo.model.UserInfo;

public interface UserService {
	public List<UserInfo> findAll();

	public void addUser(UserInfo userInfo);

	public void deleteUser(Integer id);
}
