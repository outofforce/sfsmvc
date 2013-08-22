package com.springapp.mvc;

import bean.Dao.CompanyInfoDao;
import bean.Dao.TestUserDao;
import bean.DaoImpl.CompanyInfoDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/abc")
public class abcController {

	@RequestMapping("/o")
	public String printWelcome(ModelMap model) {
		ApplicationContext context=new ClassPathXmlApplicationContext("springjdbc.xml");
		TestUserDao testUserDao=(TestUserDao)context.getBean("testUserDao");
		System.out.println(testUserDao.getUserNum()+"  getUserNmu");

		model.addAttribute("message", "Hello world!");
		return "hello";

	}
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		CompanyInfoDao companyInfoDao=new CompanyInfoDaoImpl();
		List list=companyInfoDao.getCompanyName();
		System.out.println(list);
		request.setAttribute("ComInfoList",list);
		return "/main/index";
	}

}