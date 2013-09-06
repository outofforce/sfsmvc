package com.springapp.mvc;

import bean.Dao.QueryMsgDao;
import bean.Dao.UserMsgDao;
import bean.DaoImpl.UserMsgDaoImpl;
import bean.DaoImpl.UserPublishDao;
import bean.bean.MsgRelaBean;
import bean.bean.UserPublish;
import common.BASE64;
import common.JsonPluginsUtil;
import common.WebUtil;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-28
 * Time: 上午10:21
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/")
public class MsgController {
	@Autowired
	public JdbcTemplate jdbcTemplate;
	@RequestMapping("/queryPubdata")
	public void bulletin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		MsgRelaBean relaBean= (MsgRelaBean) WebUtil.getBean(request, MsgRelaBean.class);
		ApplicationContext context=new ClassPathXmlApplicationContext("springjdbc.xml");
		QueryMsgDao dao=(QueryMsgDao)context.getBean("queryMsgDao");
		List publishList=dao.getMsgList(relaBean);
		String str= JsonPluginsUtil.beanListToJson(publishList);
		str= BASE64.encryptBASE64(String.format("success%s", str).getBytes("UTF-8"));
		//返回结果
		WebUtil.setResponse(response, str);
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
}
