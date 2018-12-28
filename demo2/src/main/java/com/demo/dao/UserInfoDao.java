package com.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.demo.model.UserInfo;

@Mapper
public interface UserInfoDao {
     public List<UserInfo> findAll();

	public UserInfo findUserByName(String username);
	
	public UserInfo findById(int id);
}
