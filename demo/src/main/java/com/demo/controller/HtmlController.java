package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.demo.model.UserInfo;
import com.demo.service.UserService;

@Controller
public class HtmlController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/hello")
    public String hello() {
    	return "hello.html";
    }
	
	/**
	 * 查询所有用户信息
	 * @return
	 */
	@RequestMapping("/findAll")
	public ModelAndView findAll() {
		List<UserInfo> uList = userService.findAll();
		return new ModelAndView("findUserInfo.html", "userInfoList", uList);
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@RequestMapping("/addUserInfo")
	public String addUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName("liu");
		userInfo.setPassword("123");
		userService.addUser(userInfo);
		return "redirect:findAll";
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteUserInfo")
	public String deleteUserInfo(Integer id) {
		userService.deleteUser(id);
		return "redirect:findAll";
	}
}
