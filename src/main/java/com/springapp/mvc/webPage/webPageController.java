package com.springapp.mvc.webPage;

import bean.Dao.LoginDao;
import bean.Dao.QueryMsgDao;
import bean.DaoImpl.LoginDaoImpl;
import bean.bean.MsgRelaBean;
import bean.bean.UserDao;
import bean.bean.UserPublish;
import com.springapp.mvc.HelloController;
import common.WebUtil;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-29
 * Time: 上午10:22
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")
public class webPageController {
	static String prefix="/webPage/";
	@RequestMapping("getMainfranme")
	public String getMainframe(HttpServletRequest request,HttpServletResponse response) throws IOException {
		UserDao user=(UserDao) WebUtil.getBean(request,UserDao.class);
		//将用户名密码放入session中
		HttpSession session=request.getSession();
		if(user==null){
			user=new UserDao();
			user.setUserName((String) session.getAttribute("userName"));
			user.setPasswd((String) session.getAttribute("passwd"));
		}
		session.setAttribute("userName",user.getUserName());
		session.setAttribute("passwd",user.getPasswd());
		ApplicationContext context=new ClassPathXmlApplicationContext("springjdbc.xml");
		LoginDao loginDao=(LoginDaoImpl)context.getBean("loginDao");
		List<UserDao> list=loginDao.getUserList(user);
		if(list.size()>0){
			user=list.get(0);
		}
		System.out.println("userId===="+user.getId());
		request.setAttribute("userId",user.getId());
		MsgRelaBean relaBean=new MsgRelaBean();
	    relaBean.setUserId(Integer.toString(user.getId()));
		QueryMsgDao dao=(QueryMsgDao)context.getBean("queryMsgDao");
		List<UserPublish> publishList=dao.getMsgList(relaBean);
		request.setAttribute("publishList",publishList);
		return prefix+"mainFrame";

	}
}
