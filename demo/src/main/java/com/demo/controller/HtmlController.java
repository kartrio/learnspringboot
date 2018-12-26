package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.demo.model.UserInfo;
import com.demo.service.UserInfoService;

@Controller
public class HtmlController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping("/hello")
    public String hello() {
    	return "hello.html";
    }
	
	@RequestMapping("/findUserInfo")
	public ModelAndView findUserInfo() {
		List<UserInfo> userInfoList = userInfoService.findUserInfo();
		return new ModelAndView("findUserInfo.html", "userInfoList", userInfoList);
	}
	
	@RequestMapping("/addUserInfo")
	public String addUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setName("liu");
		userInfo.setPwd("12345");
		userInfoService.addUserInfo(userInfo);
		return "redirect:findUserInfo";
	}
	
	@RequestMapping("/deleteUserInfo")
	public String deleteUserInfo(Integer id) {
		userInfoService.deleteUserInfo(id);
		return "redirect:findUserInfo";
	}
}
