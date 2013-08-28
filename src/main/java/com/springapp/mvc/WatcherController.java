package com.springapp.mvc;

import bean.Dao.UserRelationDao;
import bean.DaoImpl.UserRelationDaoImpl;
import bean.bean.UserDao;
import bean.bean.WatcherInfo;
import common.BASE64;
import common.JsonPluginsUtil;
import common.WebUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-28
 * Time: 上午10:22
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/")
public class WatcherController {


	@RequestMapping("/makeWatch")
	public void makeWatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String watcher=(String)request.getParameter("userId");
		String beWatcher=(String)request.getParameter("beWatcher");
		UserRelationDao dao=new UserRelationDaoImpl();
		String result=dao.watchSomeOne(watcher,beWatcher);
		WebUtil.setResponse(response, BASE64.encryptBASE64(result.getBytes()));
	}

	@RequestMapping("/cleanWatch")
	public void cleanWatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String watcher=(String)request.getParameter("userId");
		String beWatcher=(String)request.getParameter("beWatcher");
		UserRelationDao dao=new UserRelationDaoImpl();
		String result=dao.unWatchSomeOne(watcher, beWatcher);
		WebUtil.setResponse(response,BASE64.encryptBASE64(result.getBytes()));
	}

	@RequestMapping("/queryUser")
	public void queryUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String nickName=(String)request.getParameter("nickName");
		String num=(String)request.getParameter("num");
		String str=new String();
		System.out.println("nickName===="+nickName);
		UserDao user=new UserDao();
		user.setNickName(nickName);
		user.setNum(Integer.parseInt(num) * 20);
		ApplicationContext context=new ClassPathXmlApplicationContext("springjdbc.xml");
		UserRelationDao userRelationDao=(UserRelationDaoImpl)context.getBean("userRelationDao");
		List<WatcherInfo> list=userRelationDao.queryUser(user);
		if(list.size()>0){
			str= JsonPluginsUtil.beanListToJson(list);
		} else {
			str="[]";
		}
		str=String.format("success%s",str);
		//返回结果
		WebUtil.setResponse(response,BASE64.encryptBASE64(str.getBytes("UTF-8")));
		for (WatcherInfo dao:list){
			System.out.println(dao.getNickName());
		}

	}

	@RequestMapping("/myWatcher")
	public void myWatcher(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userId=(String)request.getParameter("userId");
		UserDao user=new UserDao();
		user.setId(Integer.parseInt(userId));
		String str=new String();
		ApplicationContext context=new ClassPathXmlApplicationContext("springjdbc.xml");
		UserRelationDao userRelationDao=(UserRelationDaoImpl)context.getBean("userRelationDao");
		List<WatcherInfo> list=userRelationDao.queryWatcher(user);
		if(list.size()>0){
			str= JsonPluginsUtil.beanListToJson(list);
		} else {
			str="[]";
		}
		str=String.format("success%s",str);
		//返回结果
		WebUtil.setResponse(response,BASE64.encryptBASE64(str.getBytes("UTF-8")));
		for (WatcherInfo dao:list){
			System.out.println(dao.getNickName());
		}
	}
}
