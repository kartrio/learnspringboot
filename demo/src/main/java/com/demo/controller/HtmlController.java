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
	 * 跳转到新增用户界面
	 * @return
	 */
	@RequestMapping("/addUserPage")
	public String addUserPage() {
		return "addUserInfo.html";
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@RequestMapping("/addUserInfo")
	public String addUserInfo(UserInfo userInfo) {
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
