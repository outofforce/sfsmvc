package com.springapp.mvc;

import bean.Dao.ActiveDao;
import bean.Dao.RegisterDao;
import bean.DaoImpl.ActiveDaoImpl;
import bean.DaoImpl.PublishDao;
import bean.DaoImpl.RegisterDaoImpl;
import bean.Publish;
import bean.UserDao;
import common.BASE64;
import common.JsonPluginsUtil;
import common.WebUtil;
import frame.mail.MailSenderInfo;
import frame.mail.SimpleMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
		System.out.println("username===="+userName+"\rpasswd===="+passwd);
		RegisterDao dao= (RegisterDao) new RegisterDaoImpl();
		String randomStr=dao.setRegister(userName,passwd);
		if(randomStr.equals("error")){
			str="error";
		}else {
			str="success";
			//发送邮件通知用户激活
			String content=String.format("请在手机端输入以下数字来激活您的账号：%s",randomStr);
			mailSenderInfo.setToAddress(new String[]{userName});
			SimpleMailSender simpleMailSender=new SimpleMailSender();
			mailSenderInfo.setContent(content);
			mailSenderInfo.setSubject("请点击下面的链接激活您的账户");
			simpleMailSender.sendTextMail(mailSenderInfo);
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
		WebUtil.setResponse(response, BASE64.encryptBASE64(str.getBytes("UTF-8")));
		System.out.println(str);
	}

	@RequestMapping("/active")
	public String active(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userName=(String)request.getParameter("userName");
		String value=(String)request.getParameter("value");
		ActiveDao activeDao=(ActiveDao)new ActiveDaoImpl();
		String str= activeDao.active(userName,value);
		//返回结果
		WebUtil.setResponse(response,BASE64.encryptBASE64(str.getBytes("UTF-8")));
		return null;
	}

	@RequestMapping("/queryPubdata")
	public void bulletin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userName=(String)request.getParameter("userName");
		PublishDao publishDao=new PublishDao();
		List<Publish> publishList=publishDao.getPublishList();
		String str= JsonPluginsUtil.beanListToJson(publishList);
		str= BASE64.encryptBASE64(String.format("success%s",str).getBytes("UTF-8"));
		//返回结果
		WebUtil.setResponse(response,str);
	}

	@RequestMapping("/youHaveMessage")
	public void youHaveMessage(@RequestParam("publishId")String publishId, HttpServletRequest request,HttpServletResponse response ) throws Exception {
		String sql="select count(*) from Publish where id > ?";
		Object[] objects=new Object[] {publishId};
		int i=jdbcTemplate.queryForInt(sql,objects);
		String str=BASE64.encryptBASE64(String.format("success%s",Integer.toString(i)).getBytes("UTF-8"));
		WebUtil.setResponse(response,str);

	}
}