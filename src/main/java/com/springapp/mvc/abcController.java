package com.springapp.mvc;

import bean.Dao.CompanyInfoDao;
import bean.DaoImpl.CompanyInfoDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	public JdbcTemplate jdbcTemplate;

	@RequestMapping("/o")
	public String printWelcome(ModelMap model) {
		String sql ="select * from project";
		jdbcTemplate.query(sql,new RowMapper<Object>() {
			@Override
			public Object mapRow(ResultSet resultSet, int i) throws SQLException {
				System.out.println("desc========="+resultSet.getString("desc"));
				return null;  //To change body of implemented methods use File | Settings | File Templates.
			}
		});

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