package com.demo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.model.UserInfo;
import com.demo.service.UserInfoService;

/**
 * 自定义Realm
 * @author Administrator
 *
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权逻辑");
		
		//给资源授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//添加授权字符串
		//info.addStringPermission("user:add");
		
		//到数据库查询当前登录用户的授权字符串
		//获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		int id = (int) subject.getPrincipal();
		UserInfo dbUser = userInfoService.findById(id);
		info.addStringPermission(dbUser.getPerms());
		return info;
	}

	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("执行认证逻辑");
		//编写Shiro判断逻辑,判断用户名和密码
		
		//1.判断用户名
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		
		UserInfo userInfo = userInfoService.findUserByName(token.getUsername());
		
		if(userInfo==null) {
			//用户名不存在
			return null;//Shiro底层会抛出UnknownAccountException
		}
		//判断密码
		return new SimpleAuthenticationInfo(userInfo.getId(), userInfo.getPwd(), "");
	}
  
}
