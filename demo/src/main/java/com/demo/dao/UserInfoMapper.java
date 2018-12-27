package com.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.demo.model.UserInfo;

@Mapper
public interface UserInfoMapper {
     
	public List<UserInfo> findAll();

	public void addUser(UserInfo userInfo);

	public void deleteUser(Integer id);
}
