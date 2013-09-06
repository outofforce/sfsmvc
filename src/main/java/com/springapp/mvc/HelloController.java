package com.springapp.mvc;

import bean.Dao.*;
import bean.DaoImpl.*;
import bean.bean.UserDao;
import bean.bean.UserPublish;
import bean.bean.WatcherInfo;
import common.BASE64;
import common.JsonPluginsUtil;
import common.WebUtil;
import frame.mail.MailSenderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class HelloController {
    @Autowired
    public JdbcTemplate jdbcTemplate;
	@Autowired
	public MailSenderInfo mailSenderInfo;

	@RequestMapping("/welcome")
	public String printWelcome(ModelMap model) {
		return "hello";
	}

	/**
	 * 用户注册，入参userName，passwd
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/register")
	public void resister(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//获取用户名和密码
		UserDao userDao=(UserDao) WebUtil.getBean(request,UserDao.class);
		String str=new String();
		String userName=userDao.getUserName();
		String passwd=userDao.getPasswd();
		RegisterDao dao= (RegisterDao) new RegisterDaoImpl();
		String randomStr=dao.setRegister(userDao);
		if(randomStr.equals("error")){
			str="error";
		}else if(randomStr.equals("reduplicate")){
			str=randomStr;
			WebUtil.setResponse(response, BASE64.encryptBASE64(str.getBytes("UTF-8")));
		}else {
			str="success";
			String[] strings=randomStr.split(",");
			randomStr=strings[0];
			String userId=strings[1];
			str+=userId;
			//发送邮件通知用户激活
			String content=String.format("请在手机端输入以下数字来激活您的账号：%s",randomStr);
			WebUtil.sendMail(mailSenderInfo,userName,content);
		}
		//返回结果
		WebUtil.setResponse(response, BASE64.encryptBASE64(str.getBytes("UTF-8")));
		System.out.println(str);
	}

	/**
	 * 用户登录，入参userName，passwd。返回已激活，未激活，无此用户
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println("UserName="+request.getParameter("userName"));
		System.out.println("passwd="+request.getParameter("passwd"));
		//获取用户名和密码
		UserDao userDao=(UserDao) WebUtil.getBean(request,UserDao.class);
		String flag=(String)request.getParameter("flag");
		String str=new String();
		ApplicationContext context=new ClassPathXmlApplicationContext("springjdbc.xml");
		LoginDao dao=(LoginDaoImpl)context.getBean("loginDao");
		List<UserDao> list=dao.getUserList(userDao);
		//根据返回结果查看用户状态
		if(list.size()>0){
			for (UserDao user:list){
				if(user.getStatus()==1){
					System.out.println(user.getNickName()+"id"+user.getId()+"name"+user.getUserName());
					str=String.format("success{'nick_name'='%s','head_img'='%s','user_id'='%s'}",user.getNickName(),user.getHeadImg(),user.getId());
				} else {
					str="inactive";
				}
			}
		}   else {
			str="nouser";
		}
		//返回结果
		WebUtil.setResponse(response, BASE64.encryptBASE64(str.getBytes("UTF-8")));
		System.out.println(str);
	}

	@RequestMapping("/active")
	public String active(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userId=(String)request.getParameter("userId");
		String value=(String)request.getParameter("value");
		System.out.println("userId="+userId+"\nvalue="+value);
		ActiveDao activeDao=(ActiveDao)new ActiveDaoImpl();
		String str= activeDao.active(userId,value);
		//返回结果
		WebUtil.setResponse(response,BASE64.encryptBASE64(str.getBytes("UTF-8")));
		return null;
	}


	@RequestMapping("/download")
	public String download(HttpServletRequest request,HttpServletResponse response){
		return "download" ;
	}


}