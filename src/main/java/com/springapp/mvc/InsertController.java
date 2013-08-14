package com.springapp.mvc;


import bean.CompanyInfo;
import bean.Dao.CompanyInfoDao;
import bean.Dao.RecruitInfoDao;
import bean.DaoImpl.CompanyInfoDaoImpl;
import bean.DaoImpl.RecruitInfoDaoImpl;
import bean.RecruitInfo;
import common.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-13
 * Time: 下午9:58
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/")
public class InsertController {
	@RequestMapping("/insertCompanyInfo")
	public void insertCompanyInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		CompanyInfo companyInfo= (CompanyInfo) WebUtil.getBean(request, CompanyInfo.class);
		CompanyInfoDao companyInfoDao=new CompanyInfoDaoImpl();
		String str;
		try{
			companyInfoDao.setCompanyInfo(companyInfo);
			str="success";
			WebUtil.setResponse(response,str);
		}catch (Exception e){
			WebUtil.setResponse(response,e.getMessage());
		}

	}
	@RequestMapping("/insertRecruitInfo")
	public void insertRecruitInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		RecruitInfo recruitInfo=(RecruitInfo)WebUtil.getBean(request,RecruitInfo.class);
		System.out.println("Recruit");
		RecruitInfoDao recruitInfoDao=new RecruitInfoDaoImpl();
		String str;
		try{
			recruitInfoDao.insertRecruitInfo(recruitInfo);
			str="success";
			System.out.println(str);
			WebUtil.setResponse(response,str);
		}catch (Exception e){
			System.out.println(e.getMessage());
			WebUtil.setResponse(response,e.getMessage());
		}
	}
}
