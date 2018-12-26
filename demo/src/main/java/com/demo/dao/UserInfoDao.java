package com.demo.dao;

import java.util.List;

import com.demo.model.UserInfo;

public interface UserInfoDao {
    public List<UserInfo> findUserInfo();

	public void addUserInfo(UserInfo userInfo);

	public void deleteUserInfo(Integer id);
}
