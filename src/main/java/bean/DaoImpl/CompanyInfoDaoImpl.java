package bean.DaoImpl;

import bean.CompanyInfo;
import bean.Dao.CompanyInfoDao;
import common.WebUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-13
 * Time: 下午9:53
 * To change this template use File | Settings | File Templates.
 */

public class CompanyInfoDaoImpl implements CompanyInfoDao{
	public static JdbcTemplate jdbcTemplate= WebUtil.getJdbcTemp();
	@Override
	public void setCompanyInfo(CompanyInfo companyInfo) {
		//To change body of implemented methods use File | Settings | File Templates.
		String sql="insert into CompanyInfo (company_code,company_name,company_addr,company_desc,active_time,status) values (?,?,?,?,?,?)";
		Object[] objects=new Object[]{companyInfo.getCompanyCode(),companyInfo.getCompanyName(),companyInfo.getCompanyAddr(),companyInfo.getCompanyDesc(),new Date(),1};
		jdbcTemplate.update(sql,objects);
	}

	@Override
	public List<Map<String,Object>> getCompanyName() {
		String sql="select company_code,company_name from CompanyInfo where status = 1";
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
		return list;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
