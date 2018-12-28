为什么需要使用Shiro?

Shiro核心API

Subject : 用户主体(把操作交给SecurityManager )
SecurityManager : 安全管理器(关联Realm)
Realm : Shiro连接数据的桥梁

Shiro与Spring整合依赖(在此期间出现创建Bean失败的错误可能是没有指定Bean的name的原因)

1. 修改pom.xml

2. 自定义Realm的类

3. 编写Shiro的配置类

4. 使用Shiro的过滤器进行页面拦截
   在ShiroFilterFactoryBean方法里面通过ShiroFilterFactoryBean对象的
   setFilterChainDefinitionMap方法设置需要拦截的Controller,该方法需要的参数是Map

   ```java
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
   filterMap.put("/test","anon");
   filterMap.put("/*", "authc");
   //修改要跳转的登录页面
   shiroFilterFactoryBean.setLoginUrl("/toLogin");
   shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
   ```

## 实现登录认证的操作

1. 编写登录页面

2. 处理登录逻辑

   ```java
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
   ```

3. 编写Realm的判断逻辑

   ```java
   //数据库的用户名和密码
   		String name = "kris";
   		String pwd = "1234";
   		//编写Shiro判断逻辑,判断用户名和密码
   		//1.判断用户名
   		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
   		if(!token.getUsername().equals(name)) {
   			//用户名不存在
   			return null;//Shiro底层会抛出UnknownAccountException
   		}
   		//判断密码
   		return new SimpleAuthenticationInfo("", pwd, "");
   ```

## 整合Mybatis进行认证

1. 导入Mybatis的依赖、mysql的依赖(可以有数据源连接池的依赖)

   ```java
   <!-- spring-boot整合mybatis -->
   		<dependency>
   			<groupId>org.mybatis.spring.boot</groupId>
   			<artifactId>mybatis-spring-boot-starter</artifactId>
   			<version>1.1.1</version>
   		</dependency>
   
   		<!-- 引入mysql依赖 -->
   		<dependency>
   			<groupId>mysql</groupId>
   			<artifactId>mysql-connector-java</artifactId>
   		</dependency>
   ```

   1. 在application.yml中进行数据源的配置

      ```java
      mybatis: 
         mapper-locations : classpath:com/demo/model/sqlMap/*Mapper.xml
         config-location : classpath:sqlMapConfig.xml
         type-aliases-package : com.demo.model
      spring:
         datasource : 
           driver-class-name : com.mysql.cj.jdbc.Driver
           url : jdbc:mysql://localhost:3306/springboot?serverTimezone=UTC
           username : root
           password : 1234
      ```

      1. 进行model、dao、service、controller的书写

      2. 在dao的接口上增加@Mapper注解或者在启动程序上加上@MapperScan的注解

      3. 修改UserRealm

         ```java
         //编写Shiro判断逻辑,判断用户名和密码
         		
         		//1.判断用户名
         		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
         		
         		UserInfo userInfo = userInfoService.findUserByName(token.getUsername());
         		
         		if(userInfo==null) {
         			//用户名不存在
         			return null;//Shiro底层会抛出UnknownAccountException
         		}
         		//判断密码
         		return new SimpleAuthenticationInfo("", userInfo.getPwd(), "");
         ```

## Spring Boot整合Shiro实现用户授权

只需要在配置文件中新增一个授权过滤器

```java
//授权过滤器,这一个配置需要在filterMap.put("/*", "authc");之前
//注意:当授权拦截后,Shiro会自动跳转到未授权的一个页面
filterMap.put("/add", "perms[user:add]");
//设置未授权提示页面
shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
 
```

完成Shiro资源授权,在Realm的授权逻辑中编写

```java
       //给资源授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//添加授权字符串
		info.addStringPermission("user:add");
		
		return info;
```

## 结合数据库进行授权:修改UserRealm

```java
package com.demo.shiro;
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
		UserInfo userInfo = (UserInfo) subject.getPrincipal();
		UserInfo dbUser = userInfoService.findById(userInfo.getId());
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

```

## thymeleaf和Shiro标签整合使用

导入thymeleaf的扩展依赖

在Shiro的配置类中配置一个ShiroDialect

```java
/**
	 * 配置ShiroDialect,用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
```

在页面上使用Shiro的标签

```java
<div shiro:hasPermission="user:add">
   进入用户添加功能: <a href="add">用户添加</a>
</div>
<div shiro:hasPermission="user:update">
进入用户更新功能: <a href="update">用户更新</a>
```