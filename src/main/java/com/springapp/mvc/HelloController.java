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
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.awt.AWTCharset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
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
	public  String test(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String urlStr = "http://api.map.baidu.com/geocoder?address=牡丹江路营业厅&output=json&key=6eea93095ae93db2c77be9ac910ff311&city=上海市";
		URL url = new URL(urlStr);
		URLConnection con = url.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("Pragma:", "no-cache");
		con.setRequestProperty("Cache-Control", "no-cache");
		con.setRequestProperty("Content-Type", "text/html");

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = "";
		StringBuffer buf = new StringBuffer();
		while ( (line = br.readLine()) != null ) {
			buf.append(line);
		}
		String
		result = buf.toString();

		OutputStream out1 = response.getOutputStream();
		byte[] bt=result.getBytes();
		response.setContentLength(bt.length);
		out1.write(bt);
		out1.close();
		out1.flush();
		return null;
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