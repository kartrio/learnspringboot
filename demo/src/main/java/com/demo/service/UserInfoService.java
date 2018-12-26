package com.demo.service;

import java.util.List;

import com.demo.model.UserInfo;

public interface UserInfoService {

	public List<UserInfo> findUserInfo();

	public void addUserInfo(UserInfo userInfo);

	public void deleteUserInfo(Integer id);

}
