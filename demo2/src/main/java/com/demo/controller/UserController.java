package com.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@RequestMapping("/add")
	public String add() {
		return "user/add";
	}

	@RequestMapping("/update")
	public String update() {
		return "user/update";
	}
	
	@RequestMapping("/toLogin")
	public String login() {
		return "login";
	}

	@RequestMapping("/test")
	public String hello() {
		return "test";
	}
	
	@RequestMapping("/noAuth")
	public String noAuth() {
		return "noAuthPage";
	}
	
	/**
	 * 登录逻辑处理
	 * @return
	 */
	@RequestMapping("/login")
	public String login(String name, String pwd, Model model) {
		/**
		 * 使用Shiro编写认证操作
		 */
		//1.获取Subject
		Subject subject = SecurityUtils.getSubject();
		
		//2.封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
		//3.执行登录方法
		try {
			subject.login(token);
			//登录成功
			return "test";
		} catch (UnknownAccountException e) {
			
			//登录失败:用户名不存在
			model.addAttribute("msg", "用户名不存在");
			return "login";
		} catch (IncorrectCredentialsException e) {
			//登录失败：密码错误
			model.addAttribute("msg", "密码错误");
			return "login";
		}
	}
}
