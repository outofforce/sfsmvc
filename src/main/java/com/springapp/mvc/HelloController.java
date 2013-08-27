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

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
        String sql ="select * from project";
		jdbcTemplate.execute(sql);

        model.addAttribute("message", "Hello world!");

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
		String userName=userDao.getUserName();
		String passwd=userDao.getPasswd();
		final String[] nickNameImg = new String[3];
		final int[] status = {0};
		//查询是否有此用户，用户是否已激活
		String sql="select status,nick_name,head_img,id from User where user_name = ? and passwd = ?";
		Object[] objects=new Object[] {userName,passwd};
		jdbcTemplate.query(sql,objects,new RowMapper<Object>() {
			@Override
			public Object mapRow(ResultSet resultSet, int i) throws SQLException {

				status[0] =resultSet.getInt("status");
				if(resultSet.getString("nick_name")!=null)   {
					nickNameImg[0] =resultSet.getString("nick_name");
				}
				if(resultSet.getString("head_img")!=null){
					nickNameImg[1]=resultSet.getString("head_img");
				}
				nickNameImg[2]= String.valueOf(resultSet.getInt("id"));
				return null;  //To change body of implemented methods use File | Settings | File Templates.
			}
		});
		try{
			if(1== status[0]){
				if(flag!=null&&flag.equals("1")){
					if("".equals(nickNameImg[0])||nickNameImg[0]==null){
					    nickNameImg[0]="";
					}
					if("".equals(nickNameImg[1])||nickNameImg[1]==null){
						nickNameImg[1]="";
					}
					if("".equals(nickNameImg[2])||nickNameImg[2]==null){
						nickNameImg[2]="";
					}
					str=String.format("success{'nick_name'='%s','head_img'='%s','user_id'='%s'}",nickNameImg[0],nickNameImg[1],nickNameImg[2]);
				}else {
					str="success";
				}
			}else if(0== status[0]){
				str="inactive";
			}
		}catch (Exception e){
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

	@RequestMapping("/queryPubdata")
	public void bulletin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userId=(String)request.getParameter("userId");
		UserPublishDao publishDao=new UserPublishDao();
		//List<UserPublish> publishList=publishDao.getPublishList();
		ApplicationContext context=new ClassPathXmlApplicationContext("springjdbc.xml");
		QueryMsgDao dao=(QueryMsgDao)context.getBean("queryMsgDao");
		List publishList=dao.getMsgList(userId);
		String str= JsonPluginsUtil.beanListToJson(publishList);
		str= BASE64.encryptBASE64(String.format("success%s",str).getBytes("UTF-8"));
		//返回结果
		WebUtil.setResponse(response,str);
	}

	@RequestMapping("/youHaveMessage")
	public void youHaveMessage(@RequestParam("publishId")String publishId, HttpServletRequest request,HttpServletResponse response ) throws Exception {
		String sql="select count(*) from UserPublish where id > ?";
		Object[] objects=new Object[] {publishId};
		int i=jdbcTemplate.queryForInt(sql,objects);
		String str=BASE64.encryptBASE64(String.format("success%s",Integer.toString(i)).getBytes("UTF-8"));
		WebUtil.setResponse(response,str);

	}

	@RequestMapping("/postPubData")
	public  void postPbuData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		UserPublish userPublish= (UserPublish) WebUtil.getBean(request, UserPublish.class);
		System.out.println(userPublish.getPostContext());
		UserMsgDao dao=new UserMsgDaoImpl();
		boolean b=dao.insertToUserPublish(userPublish);
		if(b){
			WebUtil.setResponse(response,BASE64.encryptBASE64("success".getBytes()));
		}else {
			WebUtil.setResponse(response,BASE64.encryptBASE64("error".getBytes()));
		}
	}



	@RequestMapping("/makeWatch")
	public void makeWatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    String watcher=(String)request.getParameter("userName");
		String beWatcher=(String)request.getParameter("beWatcher");
		UserRelationDao dao=new UserRelationDaoImpl();
		String result=dao.watchSomeOne(watcher,beWatcher);
		WebUtil.setResponse(response,BASE64.encryptBASE64(result.getBytes()));
	}

	@RequestMapping("/cleanWatch")
	public void cleanWatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String watcher=(String)request.getParameter("userName");
		String beWatcher=(String)request.getParameter("beWatcher");
		UserRelationDao dao=new UserRelationDaoImpl();
		String result=dao.unWatchSomeOne(watcher, beWatcher);
		WebUtil.setResponse(response,BASE64.encryptBASE64(result.getBytes()));
	}

	@RequestMapping("/queryUser")
	public void queryUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userName=(String)request.getParameter("userName");
		String num=(String)request.getParameter("num");
		String str=new String();
		UserDao user=new UserDao();
		user.setUserName(userName);
		user.setNum(Integer.parseInt(num)*20);
		ApplicationContext context=new ClassPathXmlApplicationContext("springjdbc.xml");
	    UserRelationDao userRelationDao=(UserRelationDaoImpl)context.getBean("userRelationDao");
		List<WatcherInfo> list=userRelationDao.queryUser(user);
		if(list.size()>0){
			str=JsonPluginsUtil.beanListToJson(list);
		} else {
			str="isNull";
		}
		str=String.format("success%s",str);
		//返回结果
		WebUtil.setResponse(response,BASE64.encryptBASE64(str.getBytes("UTF-8")));
		for (WatcherInfo dao:list){
			System.out.println(dao.getNickName());
		}

	}
}