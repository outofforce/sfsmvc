package com.springapp.mvc;

import bean.UserDao;
import common.WebUtil;
import frame.mail.MailSenderInfo;
import frame.mail.SimpleMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.awt.AWTCharset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/")
public class HelloController {
    @Autowired
    public JdbcTemplate jdbcTemplate;
	@Autowired
	public MailSenderInfo mailSenderInfo;

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
        String sql ="select * from project";
		jdbcTemplate.execute(sql);

        model.addAttribute("message", "Hello world!");

		return "hello";
	}

	@RequestMapping("/test")
	public  String test(HttpRequest request,HttpServletResponse response) throws IOException {
		System.out.println("1");
		OutputStream out = response.getOutputStream();
		String str="{return {sa ss} {11 44}}";
		byte[] bt=str.getBytes();
		response.setContentLength(bt.length);
		out.write(bt);
		out.close();
		out.flush();
		return null;
	}

	/**
	 * 用户注册，入参userName，passwd
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/register")
	public void resister(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//获取用户名和密码
		System.out.println();
		UserDao userDao=(UserDao) WebUtil.getBean(request,UserDao.class);
		String str=new String();
		String userName=userDao.getUserName();
		String passwd=userDao.getPasswd();
		//创建新用户
		System.out.println(userName+passwd);
		int id=WebUtil.getCount("User");
		Date date=new Date();
		String sql="insert into User (id,user_name,passwd,create_time,status,chg_time,email) values (?,?,?,?,0,?,?)";
		Object[] objects=new Object[]{id,userName,passwd,date,date,userName};
		try{
			jdbcTemplate.update(sql,objects);
			str="success";
		}catch (Exception e){
			System.out.println("errorMessage="+e.getMessage());
			str="error";
		}
		//返回结果
		OutputStream out = response.getOutputStream();
		byte[] bt=str.getBytes();
		response.setContentLength(bt.length);
		response.setCharacterEncoding("UTF-8");
		out.write(bt);
		out.close();
		out.flush();
		//设置激活随机码
		String randomStr=WebUtil.randomString(10);
		id=WebUtil.getCount("UserEvent");
	    sql="insert into UserEvent values (?,?,?,?,?,?,?)";
		objects=new Object[]{id,userName,0,randomStr,1,date,date};
		jdbcTemplate.update(sql,objects);

		//发送邮件通知用户激活
		String content=String.format("http://localhost:8080/active.do?userName=%s&value=%s",userName,randomStr);
		mailSenderInfo.setToAddress(new String[]{userName});
		SimpleMailSender simpleMailSender=new SimpleMailSender();
		mailSenderInfo.setContent(content);
		mailSenderInfo.setSubject("请点击下面的链接激活您的账户");
		simpleMailSender.sendTextMail(mailSenderInfo);
	}

	/**
	 * 用户登录，入参userName，passwd。返回已激活，未激活，无此用户
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//获取用户名和密码
		System.out.println();
		UserDao userDao=(UserDao) WebUtil.getBean(request,UserDao.class);
		String str=new String();
		String userName=userDao.getUserName();
		String passwd=userDao.getPasswd();
		//查询是否有此用户，用户是否已激活
		String sql="select status from User where user_name = ? and passwd = ?";
		Object[] objects=new Object[] {userName,passwd};
		Integer status=jdbcTemplate.queryForInt(sql,objects);
		try{
			if(1==status){
				str="success";
			}else if(0==status){
				str="inactive";
			}
		}catch (Exception e){
			    str="nouser";
		}
		//返回结果
		OutputStream out = response.getOutputStream();
		byte[] bt=str.getBytes();
		response.setContentLength(bt.length);
		response.setCharacterEncoding("UTF-8");
		out.write(bt);
		out.close();
		out.flush();
	}

	@RequestMapping("/active")
	public String active(HttpServletRequest request,HttpServletResponse response){
		String userName=(String)request.getParameter("userName");
		String value=(String)request.getParameter("value");
		String sql="update UserEvent set status = 0 where user_id = ? and event_value = ?";
		Object[] objects=new Object[] {userName,value};
		try{
			jdbcTemplate.update(sql,objects);
			sql="update User set status = 1 where user_name = ? ";
			jdbcTemplate.update(sql,new Object[] {userName});
			return "/main/active";
		} catch (Exception e){
			System.out.println(e.getMessage());
			return "/main/error";
		}

	}
	@RequestMapping("/bulletin")
	public void bulletin(HttpServletRequest request,HttpServletResponse response){

	}


}