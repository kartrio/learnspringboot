package com.demo.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.UserInfoDao;
import com.demo.model.UserInfo;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    private final static String URL = "jdbc:mysql://localhost:3306/digital";
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String NAME = "root";
    private final static String PWD = "1234";
    static Connection conn = null;
    
    static {
    	try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, NAME, PWD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public List<UserInfo> findUserInfo() {
		String sql = "SELECT * FROM user_info";
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(rs.getInt(1));
				userInfo.setName(rs.getString(2));
				userInfo.setPwd(rs.getString(3));
				userInfoList.add(userInfo);
			}
			rs.close();
			psmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userInfoList;
	}

	@Override
	public void addUserInfo(UserInfo userInfo) {
		String sql = "INSERT INTO user_info(userName, password) values(?, ?)";
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, userInfo.getName());
			psmt.setString(2, userInfo.getPwd());
			psmt.executeUpdate();
			psmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUserInfo(Integer id) {
		String sql = "DELETE FROM user_info WHERE id = ?";
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, id);
			psmt.executeUpdate();
			psmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
