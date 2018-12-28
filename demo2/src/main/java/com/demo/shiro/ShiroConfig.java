package com.demo.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * Shiro的配置类
 * 
 * @author Administrator
 *
 */
@Configuration
public class ShiroConfig {

	/**
	 * 3. 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(
			@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 添加Shiro的内置过滤器
		/**
		 * Shiro的内置过滤器,可以实现权限的相关的拦截
		  *   常用的过滤器: 
		 *     anon  : 无需认证(登录)可以访问 
		 *     authc : 必须认证才可以访问
		 *      user : 如果使用rememberMe的功能可以直接访问
		 *     perms : 该资源必须得到资源权限才可以访问
		 *      role : 该资源必须得到角色权限才可以访问
		 */
		Map<String, String> filterMap = new LinkedHashMap<String, String>();
		/*
		 * filterMap.put("/add", "authc"); filterMap.put("/update", "authc");
		 */
		
		//放行test请求
		filterMap.put("/test","anon");
		//放行login请求
		filterMap.put("/login","anon");
		
		//授权过滤器
	    //注意:当授权拦截后,Shiro会自动跳转到未授权的一个页面
		filterMap.put("/add", "perms[user:add]");
		filterMap.put("/update", "perms[user:update]");
		
		filterMap.put("/*", "authc");
		
		//修改要跳转的登录页面
		shiroFilterFactoryBean.setLoginUrl("/toLogin");
		
		//设置未授权提示页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 2. 创建DefaultWebSecurityManager 
	 * 使用@Qualifier("userRealm")寻找UserRealm
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 关联realm
		securityManager.setRealm(userRealm);

		return securityManager;
	}

	/**
	 * 1. 创建Realm
	 */
	@Bean(name = "userRealm")
	public UserRealm getRealm() {
		return new UserRealm();
	}
	
	/**
	 * 配置ShiroDialect,用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
}
