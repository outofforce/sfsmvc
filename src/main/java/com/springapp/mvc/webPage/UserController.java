package com.springapp.mvc.webPage;

import bean.Dao.LoginDao;
import bean.Dao.RegisterDao;
import bean.Dao.UserUpdateDao;
import bean.DaoImpl.LoginDaoImpl;
import bean.DaoImpl.RegisterDaoImpl;
import bean.DaoImpl.UserUpdateDaoImpl;
import bean.bean.UserDao;
import common.BASE64;
import common.WebUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-9-5
 * Time: 上午11:03
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")
public class UserController {
	public static ApplicationContext context=new ClassPathXmlApplicationContext("springjdbc.xml");
	 @RequestMapping("/updateImg")
	public void updateImg(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 UserDao user= (UserDao) WebUtil.getBean(request, UserDao.class);
	     String userId=(String)request.getParameter("userId");
		 user.setId(Integer.parseInt(userId));
		 UserUpdateDao dao= (UserUpdateDaoImpl) context.getBean("userUpdateDao");
		 int count=dao.updateUser(user);
		 System.out.println("returnCount=="+count);
		 String str="success"+count;
		 WebUtil.setResponse(response, BASE64.encryptBASE64(str.getBytes("UTF-8")));
	 }
}
